package io.qifan.ai.kimi.api;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.io.BufferedReader;
import java.util.List;
import java.util.concurrent.Executor;

@Slf4j
public class KimiAiApi {
    private final String apiKey;
    private final Executor executor;
    private static final String CHAT_COMPLETION_URL = "https://api.moonshot.cn/v1/chat/completions";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public KimiAiApi(String apiKey, Executor executor) {
        this.apiKey = apiKey;
        this.executor = executor;
    }

    public boolean isValidJson(String jsonString) {
        try {
            objectMapper.readTree(jsonString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Data
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ChatRequest {
        @JsonProperty("messages")
        List<ChatMessage> messages;
        @JsonProperty("model")
        String model;
        @JsonProperty("frequency_penalty")
        Float frequencyPenalty;
        @JsonProperty("max_tokens")
        Integer maxTokens;
        @JsonProperty("n")
        Integer n;
        @JsonProperty("presence_penalty")
        Float presencePenalty;
        @JsonProperty("stop")
        List<String> stop;
        @JsonProperty("stream")
        Boolean stream;
        @JsonProperty("temperature")
        Float temperature;
        @JsonProperty("top_p")
        Float topP;
    }

    @Data
    @Accessors(chain = true)
    public static class ChatMessage {
        private String role;
        private String content;
    }

    @Data
    @Accessors(chain = true)
    public static class ChatResponse {
        @JsonProperty("id")
        String id;
        @JsonProperty("object")
        String object;
        @JsonProperty("created")
        Long created;
        @JsonProperty("model")
        String model;
        @JsonProperty("choices")
        List<ChatResponseChoice> choices;
    }

    @Data
    @Accessors(chain = true)
    public static class ChatResponseChoice {
        @JsonProperty("index")
        Integer index;
        @JsonProperty("delta")
        ChatResponseMessage delta;
        @JsonProperty("finish_reason")
        String finishReason;
        @JsonProperty("usage")
        Usage usage;

    }

    public static class Usage {
        @JsonProperty("prompt_tokens")
        Integer promptTokens;
        @JsonProperty("completion_tokens")
        Integer completionTokens;
        @JsonProperty("total_tokens")
        Integer totalTokens;
    }

    @Data
    @Accessors(chain = true)
    public static class ChatResponseMessage {
        @JsonProperty("role")
        String role;
        @JsonProperty("content")
        String content;
    }

    @SneakyThrows
    public Flux<ChatResponse> chat(ChatRequest request) {
        return Flux.create(fluxSink -> {
            executor.execute(() -> chat(fluxSink, request));
        });
    }

    @SneakyThrows
    public void chat(FluxSink<ChatResponse> fluxSink, ChatRequest request) {
        Request okhttpRequest = new Request.Builder()
                .url(CHAT_COMPLETION_URL)
                .post(RequestBody.create(objectMapper.writeValueAsBytes(request), MediaType.get("application/json")))
                .addHeader("Authorization", "Bearer " + this.apiKey)
                .build();
        Call call = new OkHttpClient().newCall(okhttpRequest);
        Response okhttpResponse = call.execute();
        BufferedReader reader = new BufferedReader(okhttpResponse.body().charStream());
        String line;
        while ((line = reader.readLine()) != null) {
            if (!StringUtils.hasText(line)) {
                continue;
            }
            if (isValidJson(line)) {
                log.error("error: {}", line);
                fluxSink.complete();
                return;
            }
            line = line.replace("data: ", "");
            if (line.equals("[DONE]") || !isValidJson(line)) {
                fluxSink.complete();
            }
            ChatResponse chatCompletionChunk = objectMapper.readValue(line, ChatResponse.class);
            fluxSink.next(chatCompletionChunk);
        }

    }
}
