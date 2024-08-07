package io.qifan.ai.dashscope;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import io.reactivex.Flowable;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class DashScopeAiVLChatModel implements ChatModel {
    private final DashScopeAiApi dashScopeAiApi;
    private final DashScopeAiChatOptions defaultOptions;

    @Override
    public ChatResponse call(Prompt prompt) {
        return toResponse(dashScopeAiApi.vlChatCompletion(toParam(prompt)));
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return null;
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        MultiModalConversationParam params = toParam(prompt);
        params.setIncrementalOutput(true);
        Flowable<MultiModalConversationResult> stream = dashScopeAiApi.vlChatCompletionStream(params);
        return Flux.create(fluxSink -> {
            stream.subscribe(multiModalConversationResult -> {
                ChatResponse chatResponse = toResponse(multiModalConversationResult);
                String finishReason = chatResponse.getResult().getMetadata().getFinishReason();
                fluxSink.next(chatResponse);
                if (StringUtils.hasLength(finishReason) && finishReason.equals("stop")) {
                    fluxSink.complete();
                }
            });
        });
    }

    public MultiModalConversationParam toParam(Prompt prompt) {
        List<MultiModalMessage> modalMessages = prompt.getInstructions().stream()
                .map(message -> {
                    List<Map<String, Object>> content = new ArrayList<>();
                    content.add(Map.of("text", message.getContent()));
                    if (message instanceof UserMessage userMessage) {
                        content.addAll(userMessage.getMedia().stream().map(media -> Map.of("image", media.getData())).toList());
                    }
                    MultiModalMessage build = MultiModalMessage.builder().role(message.getMessageType().getValue())
                            .content(content)
                            .build();
                    return build;
                })
                .toList();
        DashScopeAiChatOptions options = new DashScopeAiChatOptions();
        if (defaultOptions != null) {
            options = ModelOptionsUtils.merge(defaultOptions, options, DashScopeAiChatOptions.class);
        }
        if (prompt.getOptions() != null) {
            options = ModelOptionsUtils.merge(prompt.getOptions(), defaultOptions, DashScopeAiChatOptions.class);
        }
        return MultiModalConversationParam.builder()
                .messages(modalMessages).model(options.getModel())
                .maxLength(options.getMaxTokens())
                .build();
    }

    public ChatResponse toResponse(MultiModalConversationResult result) {
        List<Generation> generations = result.getOutput()
                .getChoices()
                .stream()
                .map(choice -> {
                    return new Generation(new AssistantMessage((String) choice.getMessage().getContent().get(0).get("text")),
                            ChatGenerationMetadata.from(choice.getFinishReason(), null));
                })
                .toList();

        ChatResponseMetadata chatResponseMetadata = ChatResponseMetadata.builder()
                .withUsage(new Usage() {
                    @Override
                    public Long getPromptTokens() {
                        return result.getUsage().getInputTokens().longValue();
                    }

                    @Override
                    public Long getGenerationTokens() {
                        return result.getUsage().getOutputTokens().longValue();
                    }
                })
                .build();
        return new ChatResponse(generations, chatResponseMetadata);
    }
}
