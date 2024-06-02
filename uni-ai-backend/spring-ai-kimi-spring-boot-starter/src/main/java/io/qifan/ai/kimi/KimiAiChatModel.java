package io.qifan.ai.kimi;

import io.qifan.ai.kimi.api.KimiAiApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.retry.support.RetryTemplate;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
public class KimiAiChatModel implements ChatModel {
    private final KimiAiApi kimiAiApi;
    private final RetryTemplate retryTemplate;

    public KimiAiChatModel(KimiAiApi kimiAiApi, RetryTemplate retryTemplate) {
        this.kimiAiApi = kimiAiApi;
        this.retryTemplate = retryTemplate;
    }

    public KimiAiChatModel(KimiAiApi kimiAiApi) {
        this(kimiAiApi, RetryUtils.DEFAULT_RETRY_TEMPLATE);
    }


    @Override
    public ChatResponse call(Prompt prompt) {
        KimiAiApi.ChatRequest request = toRequest(prompt, false);
        return this.retryTemplate.execute(ctx -> {
            KimiAiApi.ChatResponse body = kimiAiApi.chatCompletion(request).getBody();
            if (body == null) {
                log.warn("此次请求:{} 的结构为空", prompt);
                return new ChatResponse(List.of());
            }
            return toResponse(body);
        });
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return null;
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        KimiAiApi.ChatRequest request = toRequest(prompt, true);
        return this.retryTemplate
                .execute(ctx -> kimiAiApi.chatCompletionStream(request)
                        .map(this::toResponse));
    }

    ChatResponse toResponse(KimiAiApi.ChatResponse response) {
        List<Generation> generations = response.getChoices()
                .stream()
                .map(choice -> {
                    return new Generation(choice.getMessage().getContent())
                            .withGenerationMetadata(ChatGenerationMetadata.from(choice.getFinishReason() != null ? choice.getFinishReason() : "", null));
                })
                .toList();
        ChatResponseMetadata chatResponseMetadata = new ChatResponseMetadata.DefaultChatResponseMetadata() {
            @Override
            public Usage getUsage() {
                return new Usage() {
                    @Override
                    public Long getPromptTokens() {
                        return response.getUsage().getPromptTokens().longValue();
                    }

                    @Override
                    public Long getGenerationTokens() {
                        return response.getUsage().getCompletionTokens().longValue();
                    }
                };
            }
        };
        return new ChatResponse(generations, chatResponseMetadata);
    }

    ChatResponse toResponse(KimiAiApi.ChatResponseChunk chunk) {
        List<Generation> generations = chunk.getChoices()
                .stream()
                .map(choice -> {
                    return new Generation(choice.getDelta().getContent())
                            .withGenerationMetadata(ChatGenerationMetadata.from(choice.getFinishReason() != null ? choice.getFinishReason() : "", null));
                })
                .toList();
        ChatResponseMetadata chatResponseMetadata = new ChatResponseMetadata.DefaultChatResponseMetadata() {
            @Override
            public Usage getUsage() {
                return new Usage() {
                    @Override
                    public Long getPromptTokens() {
                        return chunk.getChoices().get(0).getUsage().getPromptTokens().longValue();
                    }

                    @Override
                    public Long getGenerationTokens() {
                        return chunk.getChoices().get(0).getUsage().getCompletionTokens().longValue();
                    }
                };
            }
        };

        return new ChatResponse(generations, chatResponseMetadata);

    }

    KimiAiApi.ChatRequest toRequest(Prompt prompt, boolean stream) {
        KimiAiChatOptions options = (KimiAiChatOptions) prompt.getOptions();
        List<KimiAiApi.ChatMessage> messages = prompt.getInstructions().stream().map(message -> new KimiAiApi.ChatMessage().setRole(message.getMessageType().getValue())
                .setContent(message.getContent())).toList();
        return new KimiAiApi.ChatRequest()
                .setModel(options.getModel())
                .setStream(stream)
                .setMessages(messages);
    }
}
