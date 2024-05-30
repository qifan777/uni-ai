package io.qifan.ai.qianfan;

import com.baidubce.qianfan.model.chat.ChatRequest;
import com.baidubce.qianfan.model.chat.Message;
import io.qifan.ai.qianfan.api.QianFanApi;
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

public class QianFanAiChatModel implements ChatModel {
    private final QianFanApi qianFanApi;


    public QianFanAiChatModel(QianFanApi qianFanApi) {
        this.qianFanApi = qianFanApi;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        return toResponse(qianFanApi.chatCompletion(toRequest(prompt)));
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return null;
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        return qianFanApi.chatCompletionStream(toRequest(prompt))
                .map(this::toResponse);
    }

    public ChatResponse toResponse(com.baidubce.qianfan.model.chat.ChatResponse response) {
        Boolean end = response.getEnd();
        Generation generation = new Generation(response.getResult())
                .withGenerationMetadata(ChatGenerationMetadata.from(end != null && end ? "STOP" : "", null));
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
        return new ChatResponse(List.of(generation), chatResponseMetadata);
    }

    public ChatRequest toRequest(Prompt prompt) {
        List<Message> messages = prompt.getInstructions()
                .stream()
                .map(message -> new Message()
                        .setContent(message.getContent())
                        .setRole(message.getMessageType().getValue())
                )
                .toList();
        QianFanAiChatOptions options = (QianFanAiChatOptions) prompt.getOptions();
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setMessages(messages);
        if (StringUtils.hasText(options.getModel())) {
            chatRequest.setModel(options.getModel());
        }
        if (options.getTemperature() != null) {
            chatRequest.setTemperature(options.getTemperature().doubleValue());
        }
        if (options.getTopP() != null) {
            chatRequest.setTopP(options.getTopP().doubleValue());
        }
        return chatRequest;
    }
}
