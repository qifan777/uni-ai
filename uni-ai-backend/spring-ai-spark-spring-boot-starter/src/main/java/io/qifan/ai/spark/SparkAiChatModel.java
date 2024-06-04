package io.qifan.ai.spark;

import io.qifan.ai.spark.api.SparkAiApi;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class SparkAiChatModel implements ChatModel {
    private final SparkAiApi sparkAiApi;
    private final SparkAiChatOptions defaultOptions;


    @Override
    public ChatResponse call(Prompt prompt) {
        return toResponse(sparkAiApi.chatCompletion(toRequest(prompt)));
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return defaultOptions;
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        SparkAiApi.SparkRequest sparkRequest = toRequest(prompt);
        return sparkAiApi.chatCompletionStream(sparkRequest).map(this::toResponse);
    }

    public ChatResponse toResponse(SparkAiApi.SparkResponse value) {
        String content = String.join("", value.getPayload().getChoices().getText().stream().map(SparkAiApi.SparkResponse.Payload.Choices.Text::getContent).toList());
        List<Generation> generations = List.of(new Generation(content)
                .withGenerationMetadata(new ChatGenerationMetadata() {
                    @Override
                    public <T> T getContentFilterMetadata() {
                        return null;
                    }

                    @Override
                    public String getFinishReason() {
                        return value.getHeader().getStatus().equals(2) ? "STOP" : "";
                    }
                }));
        ChatResponseMetadata chatResponseMetadata = new ChatResponseMetadata.DefaultChatResponseMetadata() {
            @Override
            public Usage getUsage() {
                return new Usage() {
                    @Override
                    public Long getPromptTokens() {
                        return value.getPayload().getUsage().getText().getPromptTokens().longValue();
                    }

                    @Override
                    public Long getGenerationTokens() {
                        return value.getPayload().getUsage().getText().getCompletionTokens().longValue();
                    }
                };
            }
        };
        return new ChatResponse(generations, chatResponseMetadata);
    }

    public SparkAiApi.SparkRequest toRequest(Prompt prompt) {
        SparkAiChatOptions options = (SparkAiChatOptions) prompt.getOptions();
        SparkAiApi.SparkRequest.Parameter.Chat chat = new SparkAiApi.SparkRequest.Parameter.Chat();
        if (options != null) {
            if (StringUtils.hasText(options.getDomain())) {
                chat.setDomain(options.getDomain());
            }
            if (StringUtils.hasText(options.getBaseUrl())) {
                chat.setBaseUrl(options.getBaseUrl());
            }
            if (options.getTopK() != null) {
                chat.setTopK(options.getTopK());
            }
            if (options.getTemperature() != null) {
                chat.setTemperature(options.getTemperature());
            }
            if (options.getMaxTokens() != null) {
                chat.setMaxTokens(options.getMaxTokens());
            }
        }
        return new SparkAiApi.SparkRequest()
                .setHeader(new SparkAiApi.SparkRequest.Header()
                        .setUid(UUID.randomUUID().toString().substring(0, 10)))
                .setParameter(new SparkAiApi.SparkRequest.Parameter()
                        .setChat(chat))
                .setPayload(new SparkAiApi.SparkRequest.Payload()
                        .setMessage(new SparkAiApi.SparkRequest.Message()
                                .setText(prompt.getInstructions().stream().map(value -> new SparkAiApi.SparkRequest.RoleContent()
                                        .setRole(value.getMessageType().getValue())
                                        .setContent(value.getContent())).toList())));

    }
}
