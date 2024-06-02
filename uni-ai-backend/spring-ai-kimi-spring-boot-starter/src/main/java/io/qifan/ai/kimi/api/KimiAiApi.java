package io.qifan.ai.kimi.api;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Slf4j
public class KimiAiApi {
    private final String apiKey;
    private final String baseUrl;
    private static final Predicate<String> SSE_DONE_PREDICATE = "[DONE]"::equals;

    private static final String CHAT_COMPLETION_URL = "https://api.moonshot.cn/v1/chat/completions";
    private final RestClient restClient;

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KimiAiApi(String apiKey, String baseUrl, RestClient.Builder restClientBuilder, WebClient.Builder webClientBuilder) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
        this.restClient = restClientBuilder
                .baseUrl(this.baseUrl)
                .defaultHeaders(getJsonContentHeaders(this.apiKey))
                .build();
        this.webClient = webClientBuilder
                .baseUrl(this.baseUrl)
                .defaultHeaders(getJsonContentHeaders(this.apiKey))
                .build();
    }

    @SneakyThrows
    public ChatResponseChunk toResponse(String jsonString) {
        log.info("响应结果: {}", jsonString);
        return objectMapper.readValue(jsonString, ChatResponseChunk.class);
    }

    public static Consumer<HttpHeaders> getJsonContentHeaders(String apiKey) {
        return (headers) -> {
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);
        };
    }

    ;

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
    public static class ChatResponseChunk {
        @JsonProperty("id")
        String id;
        @JsonProperty("object")
        String object;
        @JsonProperty("created")
        Long created;
        @JsonProperty("model")
        String model;
        @JsonProperty("choices")
        List<ChatResponseChunkChoice> choices;
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
        @JsonProperty("usage")
        Usage usage;
    }


    @Data
    @Accessors(chain = true)
    public static class ChatResponseChunkChoice {
        @JsonProperty("index")
        Integer index;
        @JsonProperty("delta")
        ChatResponseMessage delta;
        @JsonProperty("finish_reason")
        String finishReason;
        @JsonProperty("usage")
        Usage usage;

    }

    @Data
    @Accessors(chain = true)
    public static class ChatResponseChoice {
        @JsonProperty("index")
        Integer index;
        @JsonProperty("message")
        ChatResponseMessage message;
        @JsonProperty("finish_reason")
        String finishReason;
    }

    @Data
    @Accessors(chain = true)
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

    public Flux<ChatResponseChunk> chatCompletionStream(ChatRequest request) {
        request.setStream(true);
        return this.webClient
                .post()
                .uri("/v1/chat/completions")
                .body(Mono.just(request), ChatRequest.class)
                .retrieve()
                .bodyToFlux(String.class)
                .takeUntil(SSE_DONE_PREDICATE)
                .filter(SSE_DONE_PREDICATE.negate())
                .map(this::toResponse);
    }

    public ResponseEntity<ChatResponse> chatCompletion(ChatRequest request) {
        request.setStream(false);
        RestClient.ResponseSpec retrieve = this.restClient
                .post()
                .uri("/v1/chat/completions")
                .body(request)
                .retrieve();
        return retrieve.toEntity(ChatResponse.class);
    }
}
