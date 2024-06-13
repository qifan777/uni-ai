package io.qifan.ai.kimi.api;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.model.ModelDescription;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Slf4j
public class KimiAiApi {
    private static final Predicate<String> SSE_DONE_PREDICATE = "[DONE]"::equals;
    private final RestClient restClient;
    private final WebClient webClient;
    private KimiAiStreamFunctionCallingHelper chunkMerger = new KimiAiStreamFunctionCallingHelper();

    public KimiAiApi(String apiKey, String baseUrl, RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder) {
        String baseUrl1 = StringUtils.hasText(baseUrl) ? baseUrl : "https://api.moonshot.cn";
        this.restClient = restClientBuilder
                .baseUrl(baseUrl1)
                .defaultHeaders(getJsonContentHeaders(apiKey))
                .build();
        this.webClient = webClientBuilder
                .baseUrl(baseUrl1)
                .defaultHeaders(getJsonContentHeaders(apiKey))
                .build();
    }

    public static Consumer<HttpHeaders> getJsonContentHeaders(String apiKey) {
        return (headers) -> {
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
        };
    }

    public ResponseEntity<ChatCompletion> chatCompletionEntity(ChatCompletionRequest chatRequest) {
        Assert.notNull(chatRequest, "The request body can not be null.");
        Assert.isTrue(!chatRequest.stream(), "Request must set the steam property to false.");
        return this.restClient.post()
                .uri("/v1/chat/completions", new Object[0])
                .body(chatRequest)
                .retrieve()
                .toEntity(ChatCompletion.class);
    }

    public Flux<ChatCompletionChunk> chatCompletionStream(ChatCompletionRequest chatRequest) {
        Assert.notNull(chatRequest, "The request body can not be null.");
        Assert.isTrue(chatRequest.stream(), "Request must set the steam property to true.");
        AtomicBoolean isInsideTool = new AtomicBoolean(false);
        return this.webClient
                .post()
                .uri("/v1/chat/completions", new Object[0])
                .body(Mono.just(chatRequest), ChatCompletionRequest.class).retrieve().bodyToFlux(String.class)
                .takeUntil(SSE_DONE_PREDICATE)
                .filter(SSE_DONE_PREDICATE.negate())
                .map((content) -> {
                    return ModelOptionsUtils.jsonToObject(content, ChatCompletionChunk.class);
                })
                .map((chunk) -> {
                    if (this.chunkMerger.isStreamingToolFunctionCall(chunk)) {
                        isInsideTool.set(true);
                    }
                    return chunk;
                })
                .windowUntil((chunk) -> {
                    if (isInsideTool.get() && this.chunkMerger.isStreamingToolFunctionCallFinish(chunk)) {
                        isInsideTool.set(false);
                        return true;
                    } else {
                        return !isInsideTool.get();
                    }
                })
                .concatMapIterable((window) -> {
                    Mono<ChatCompletionChunk> monoChunk = window.reduce(new ChatCompletionChunk(null, null, null, null, null, null),
                            (previous, current) -> this.chunkMerger.merge(previous, current));
                    return List.of(monoChunk);
                })
                .flatMap((mono) -> mono);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ChatCompletionRequest(@JsonProperty("messages") List<ChatCompletionMessage> messages,
                                        @JsonProperty("model") String model,
                                        @JsonProperty("frequency_penalty") Float frequencyPenalty,
                                        @JsonProperty("max_tokens") Integer maxTokens,
                                        @JsonProperty("n") Integer n,
                                        @JsonProperty("presence_penalty") Float presencePenalty,
                                        @JsonProperty("stop") List<String> stop,
                                        @JsonProperty("stream") Boolean stream,
                                        @JsonProperty("temperature") Float temperature,
                                        @JsonProperty("top_p") Float topP,
                                        @JsonProperty("tools") List<FunctionTool> tools,
                                        @JsonProperty("tool_choice") Object toolChoice,
                                        @JsonProperty("user") String user) {
        public ChatCompletionRequest(List<ChatCompletionMessage> messages, String model, Float temperature) {
            this(messages, model, null, null, null, null, null, false, temperature, null, null, null, null);
        }

        public ChatCompletionRequest(List<ChatCompletionMessage> messages, String model, Float temperature, boolean stream) {
            this(messages, model, null, null, null, null, null, stream, temperature, null, null, null, null);
        }

        public ChatCompletionRequest(List<ChatCompletionMessage> messages, String model, List<FunctionTool> tools, Object toolChoice) {
            this(messages, model, null, null, null, null, null, false, null, null, tools, toolChoice, null);
        }

        public ChatCompletionRequest(List<ChatCompletionMessage> messages, Boolean stream) {
            this(messages, null, null, null, null, null, null, stream, null, null, null, null, null);
        }


        @JsonProperty("messages")
        public List<ChatCompletionMessage> messages() {
            return this.messages;
        }

        @JsonProperty("model")
        public String model() {
            return this.model;
        }

        @JsonProperty("frequency_penalty")
        public Float frequencyPenalty() {
            return this.frequencyPenalty;
        }


        @JsonProperty("max_tokens")
        public Integer maxTokens() {
            return this.maxTokens;
        }

        @JsonProperty("n")
        public Integer n() {
            return this.n;
        }

        @JsonProperty("presence_penalty")
        public Float presencePenalty() {
            return this.presencePenalty;
        }

        @JsonProperty("stop")
        public List<String> stop() {
            return this.stop;
        }

        @JsonProperty("stream")
        public Boolean stream() {
            return this.stream;
        }

        @JsonProperty("temperature")
        public Float temperature() {
            return this.temperature;
        }

        @JsonProperty("top_p")
        public Float topP() {
            return this.topP;
        }

        @JsonProperty("tools")
        public List<FunctionTool> tools() {
            return this.tools;
        }

        @JsonProperty("tool_choice")
        public Object toolChoice() {
            return this.toolChoice;
        }

        @JsonProperty("user")
        public String user() {
            return this.user;
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public record ResponseFormat(String type) {
            public ResponseFormat(@JsonProperty("type") String type) {
                this.type = type;
            }

            @JsonProperty("type")
            public String type() {
                return this.type;
            }
        }

        public static class ToolChoiceBuilder {
            public static final String AUTO = "auto";
            public static final String NONE = "none";

            public ToolChoiceBuilder() {
            }

            public static Object FUNCTION(String functionName) {
                return Map.of("type", "function", "function", Map.of("name", functionName));
            }
        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ChatCompletion(@JsonProperty("id") String id, @JsonProperty("choices") List<Choice> choices,
                                 @JsonProperty("created") Long created, @JsonProperty("model") String model,
                                 @JsonProperty("system_fingerprint") String systemFingerprint,
                                 @JsonProperty("object") String object, @JsonProperty("usage") Usage usage) {
        @JsonProperty("id")
        public String id() {
            return this.id;
        }

        @JsonProperty("choices")
        public List<Choice> choices() {
            return this.choices;
        }

        @JsonProperty("created")
        public Long created() {
            return this.created;
        }

        @JsonProperty("model")
        public String model() {
            return this.model;
        }

        @JsonProperty("system_fingerprint")
        public String systemFingerprint() {
            return this.systemFingerprint;
        }

        @JsonProperty("object")
        public String object() {
            return this.object;
        }

        @JsonProperty("usage")
        public Usage usage() {
            return this.usage;
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public record Choice(ChatCompletionFinishReason finishReason, Integer index,
                             ChatCompletionMessage message) {
            public Choice(@JsonProperty("finish_reason") ChatCompletionFinishReason finishReason, @JsonProperty("index") Integer index, @JsonProperty("message") ChatCompletionMessage message) {
                this.finishReason = finishReason;
                this.index = index;
                this.message = message;
            }

            @JsonProperty("finish_reason")
            public ChatCompletionFinishReason finishReason() {
                return this.finishReason;
            }

            @JsonProperty("index")
            public Integer index() {
                return this.index;
            }

            @JsonProperty("message")
            public ChatCompletionMessage message() {
                return this.message;
            }

        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ChatCompletionChunk(String id, List<ChunkChoice> choices, Long created, String model,
                                      String systemFingerprint, String object) {
        public ChatCompletionChunk(@JsonProperty("id") String id, @JsonProperty("choices") List<ChunkChoice> choices, @JsonProperty("created") Long created, @JsonProperty("model") String model, @JsonProperty("system_fingerprint") String systemFingerprint, @JsonProperty("object") String object) {
            this.id = id;
            this.choices = choices;
            this.created = created;
            this.model = model;
            this.systemFingerprint = systemFingerprint;
            this.object = object;
        }

        @JsonProperty("id")
        public String id() {
            return this.id;
        }

        @JsonProperty("choices")
        public List<ChunkChoice> choices() {
            return this.choices;
        }

        @JsonProperty("created")
        public Long created() {
            return this.created;
        }

        @JsonProperty("model")
        public String model() {
            return this.model;
        }

        @JsonProperty("system_fingerprint")
        public String systemFingerprint() {
            return this.systemFingerprint;
        }

        @JsonProperty("object")
        public String object() {
            return this.object;
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public record ChunkChoice(ChatCompletionFinishReason finishReason, Integer index, ChatCompletionMessage delta,
                                  Usage usage) {
            public ChunkChoice(@JsonProperty("finish_reason") ChatCompletionFinishReason finishReason, @JsonProperty("index") Integer index, @JsonProperty("delta") ChatCompletionMessage delta, @JsonProperty("usage") Usage usage) {
                this.finishReason = finishReason;
                this.index = index;
                this.delta = delta;
                this.usage = usage;
            }

            @JsonProperty("finish_reason")
            public ChatCompletionFinishReason finishReason() {
                return this.finishReason;
            }

            @JsonProperty("index")
            public Integer index() {
                return this.index;
            }

            @JsonProperty("delta")
            public ChatCompletionMessage delta() {
                return this.delta;
            }

            @JsonProperty("usage")
            public Usage usage() {
                return this.usage;
            }
        }
    }


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ChatCompletionMessage(Object rawContent, Role role, String name, String toolCallId,
                                        List<ToolCall> toolCalls) {
        public ChatCompletionMessage(Object content, Role role) {
            this(content, role, null, null, null);
        }

        public ChatCompletionMessage(@JsonProperty("content") Object rawContent, @JsonProperty("role") Role role, @JsonProperty("name") String name, @JsonProperty("tool_call_id") String toolCallId, @JsonProperty("tool_calls") List<ToolCall> toolCalls) {
            this.rawContent = rawContent;
            this.role = role;
            this.name = name;
            this.toolCallId = toolCallId;
            this.toolCalls = toolCalls;
        }

        public String content() {
            if (this.rawContent == null) {
                return null;
            } else {
                Object var2 = this.rawContent;
                if (var2 instanceof String) {
                    return (String) var2;
                } else {
                    throw new IllegalStateException("The content is not a string!");
                }
            }
        }

        @JsonProperty("content")
        public Object rawContent() {
            return this.rawContent;
        }

        @JsonProperty("role")
        public Role role() {
            return this.role;
        }

        @JsonProperty("name")
        public String name() {
            return this.name;
        }

        @JsonProperty("tool_call_id")
        public String toolCallId() {
            return this.toolCallId;
        }

        @JsonProperty("tool_calls")
        public List<ToolCall> toolCalls() {
            return this.toolCalls;
        }

        public enum Role {
            @JsonProperty("system")
            SYSTEM,
            @JsonProperty("user")
            USER,
            @JsonProperty("assistant")
            ASSISTANT,
            @JsonProperty("tool")
            TOOL;

            private Role() {
            }
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public record ChatCompletionFunction(@JsonProperty("name") String name,
                                             @JsonProperty("arguments") String arguments) {
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public record ToolCall(@JsonProperty("id") String id, @JsonProperty("type") String type,
                               @JsonProperty("function") ChatCompletionFunction function) {
        }

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public record MediaContent(String type, String text, ImageUrl imageUrl) {
            public MediaContent(String text) {
                this("text", text, null);
            }

            public MediaContent(ImageUrl imageUrl) {
                this("image_url", null, imageUrl);
            }

            public MediaContent(@JsonProperty("type") String type, @JsonProperty("text") String text, @JsonProperty("image_url") ImageUrl imageUrl) {
                this.type = type;
                this.text = text;
                this.imageUrl = imageUrl;
            }

            @JsonProperty("type")
            public String type() {
                return this.type;
            }

            @JsonProperty("text")
            public String text() {
                return this.text;
            }

            @JsonProperty("image_url")
            public ImageUrl imageUrl() {
                return this.imageUrl;
            }

            @JsonInclude(JsonInclude.Include.NON_NULL)
            public record ImageUrl(String url, String detail) {
                public ImageUrl(String url) {
                    this(url, (String) null);
                }

                public ImageUrl(@JsonProperty("url") String url, @JsonProperty("detail") String detail) {
                    this.url = url;
                    this.detail = detail;
                }

                @JsonProperty("url")
                public String url() {
                    return this.url;
                }

                @JsonProperty("detail")
                public String detail() {
                    return this.detail;
                }
            }
        }
    }

    public enum ChatModel implements ModelDescription {
        MOONSHOT_V1_8K("moonshot-v1-8k"),
        MOONSHOT_V1_32K("moonshot-v1-32k"),
        MOONSHOT_V1_128K("moonshot-v1-128K");

        public final String value;

        private ChatModel(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

        public String getModelName() {
            return this.value;
        }
    }

    public enum ChatCompletionFinishReason {
        @JsonProperty("stop")
        STOP,
        @JsonProperty("length")
        LENGTH,
        @JsonProperty("content_filter")
        CONTENT_FILTER,
        @JsonProperty("tool_calls")
        TOOL_CALLS,
        @JsonProperty("function_call")
        FUNCTION_CALL,
        @JsonProperty("tool_call")
        TOOL_CALL;

        private ChatCompletionFinishReason() {
        }
    }

    public record Usage(@JsonProperty("prompt_tokens") Long promptTokens,
                        @JsonProperty("completion_tokens") Long completionTokens,
                        @JsonProperty("total_tokens") Long totalTokens) {
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record FunctionTool(Type type, Function function) {
        @ConstructorBinding
        public FunctionTool(Function function) {
            this(KimiAiApi.FunctionTool.Type.FUNCTION, function);
        }

        public FunctionTool(@JsonProperty("type") Type type, @JsonProperty("function") Function function) {
            this.type = type;
            this.function = function;
        }

        @JsonProperty("type")
        public Type type() {
            return this.type;
        }

        @JsonProperty("function")
        public Function function() {
            return this.function;
        }

        public enum Type {
            @JsonProperty("function")
            FUNCTION;

            Type() {
            }
        }

        public record Function(String description, String name, Map<String, Object> parameters) {
            @ConstructorBinding
            public Function(String description, String name, String jsonSchema) {
                this(description, name, ModelOptionsUtils.jsonToMap(jsonSchema));
            }

            public Function(@JsonProperty("description") String description, @JsonProperty("name") String name, @JsonProperty("parameters") Map<String, Object> parameters) {
                this.description = description;
                this.name = name;
                this.parameters = parameters;
            }

            @JsonProperty("description")
            public String description() {
                return this.description;
            }

            @JsonProperty("name")
            public String name() {
                return this.name;
            }

            @JsonProperty("parameters")
            public Map<String, Object> parameters() {
                return this.parameters;
            }
        }
    }
}
