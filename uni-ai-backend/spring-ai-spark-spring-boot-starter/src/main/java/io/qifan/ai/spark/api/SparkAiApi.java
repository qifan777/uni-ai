package io.qifan.ai.spark.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executor;

@Slf4j
@Data
public class SparkAiApi {
    private final String apiKey;
    private final String apiSecret;
    private final String appId;
    private final ObjectMapper objectMapper;
    private final Executor executor;

    public SparkAiApi(String apiKey, String apiSecret,
                      String appId,
                      ObjectMapper objectMapper, Executor executor) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.appId = appId;
        this.objectMapper = objectMapper;
        this.executor = executor;
    }

    public Flux<SparkResponse> chat(SparkRequest sparkRequest) {
        return Flux.create(fluxSink -> executor.execute(() -> chat(fluxSink, sparkRequest)));
    }

    @SneakyThrows
    public void chat(FluxSink<SparkResponse> fluxSink, SparkRequest sparkRequest) {
        sparkRequest.getHeader().setAppId(appId);
        String url = getAuthUrl(sparkRequest.getParameter().getChat().getBaseUrl(), apiKey, apiSecret).replaceAll("https://", "wss://");
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url).build();
        WebSocket webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
                log.info("websocket连接关闭");
                webSocket.close(1002, "websocket finish");
                client.connectionPool().evictAll();
                fluxSink.complete();
            }

            @SneakyThrows
            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                super.onOpen(webSocket, response);
                System.out.println("建立websocket连接");
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);
                log.error("websocket连接失败", t);
                if (response != null) {
                    log.error("onFailure code:{}, body:{}", response.code(), response.body());
                }
                webSocket.close(1001, "websocket finish");
                client.connectionPool().evictAll();
            }

            @SneakyThrows
            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
                SparkResponse sparkResponse = objectMapper.readValue(text, SparkResponse.class);
                if (sparkResponse.getHeader().getCode() != 0) {
                    log.info("发生错误，错误码为：{}", sparkResponse.getHeader().getCode());
                    log.info("本次请求的sid为：{}", sparkResponse.getHeader().getSid());
                    webSocket.close(1000, "");
                }
                log.info(sparkResponse.toString());
                fluxSink.next(sparkResponse);
                if (sparkResponse.getHeader().getStatus() == 2) {
                    webSocket.close(1000, "websocket finish");
                }
            }
        });
        webSocket.send(objectMapper.writeValueAsString(sparkRequest));
    }

    @SneakyThrows
    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                        "date: " + date + "\n" +
                        "GET " + url.getPath() + " HTTP/1.1";
        // System.err.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // System.err.println(sha);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();
        return httpUrl.toString();
    }

    @Data
    @Accessors(chain = true)
    public static class SparkRequest {
        Header header;
        Parameter parameter;
        Payload payload;

        @Data
        @Accessors(chain = true)
        public static class Header {
            @JsonProperty(value = "app_id")
            String appId;
            String uid;
        }

        @Data
        @Accessors(chain = true)
        public static class Parameter {
            private Chat chat;

            @Data
            @Accessors(chain = true)
            public static class Chat {
                private String domain;
                private Float temperature;
                @JsonProperty(value = "max_tokens")
                private Integer maxTokens;
                @JsonProperty(value = "top_k")
                private Integer topK;
                @JsonIgnore
                private String baseUrl;
            }
        }

        @Data
        @Accessors(chain = true)
        public static class Payload {
            private Message message;
            private FunctionCall functions;
        }

        @Data
        @Accessors(chain = true)
        public static class Message {
            private List<RoleContent> text;
        }

        @Data
        @Accessors(chain = true)
        public static class RoleContent {
            private String role;
            private String content;
        }

        @Data
        @Accessors(chain = true)
        public static class FunctionCall {
            private List<Function> text;

            @Data
            @Accessors(chain = true)
            public static class Function {
                private String name;
                private String description;
                private List<FunctionParameter> parameters;
            }

            @Data
            @Accessors(chain = true)
            public static class FunctionParameter {
                private String type;
                private Map<String, ParameterProperty> properties;
                private List<String> required;
            }

            @Data
            @Accessors(chain = true)
            public static class ParameterProperty {
                private String type;
                private String description;
            }
        }
    }

    @Data
    public static class SparkResponse {
        private Header header;
        private Payload payload;

        @Data
        public static class Header {
            private Integer code;
            private Integer status;
            private String sid;
            private String message;
        }

        @Data
        public static class Payload {
            private Choices choices;
            private Usage usage;
            private Feature feature;

            @Data
            public static class Feature {
                private String format;
                private String encoding;
                private String text;
                private String compress;
            }

            @Data
            public static class Usage {
                Text text;

                @Data
                public static class Text {
                    @JsonProperty("question_tokens")
                    private Integer questionTokens;
                    @JsonProperty("prompt_tokens")
                    private Integer promptTokens;
                    @JsonProperty("completion_tokens")
                    private Integer completionTokens;
                    @JsonProperty(value = "total_tokens")
                    private Integer totalTokens;
                }
            }

            @Data
            public static class Choices {
                private Integer status;
                private Integer seq;
                private List<Text> text;

                @Data
                public static class Text {
                    private String role;
                    private String content;
                    private Integer index;
                    private FunctionCall function_call;
                }
            }

        }

        @Data
        public static class FunctionCall {
            private String arguments;
            private String name;
        }
    }
}