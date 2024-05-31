package io.qifan.ai.kimi;

import io.qifan.ai.kimi.api.KimiAiApi;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

import java.util.List;

public class KimiAiChatModel implements ChatModel {
    private final KimiAiApi kimiAiApi;

    public KimiAiChatModel(KimiAiApi kimiAiApi) {
        this.kimiAiApi = kimiAiApi;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return null;
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        return kimiAiApi.chat(toRequest(prompt, true)).map(this::toResponse);
    }

    ChatResponse toResponse(KimiAiApi.ChatResponse chunk) {
        List<Generation> generations = chunk.getChoices()
                .stream()
                .map(choice -> {
                    return new Generation(choice.getDelta().getContent())
                            .withGenerationMetadata(ChatGenerationMetadata.from(choice.getFinishReason() != null ? choice.getFinishReason() : "", null));
                })
                .toList();
        return new ChatResponse(generations);

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
