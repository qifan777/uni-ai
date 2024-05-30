package io.qifan.ai.dashscope;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import io.reactivex.Flowable;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DashScopeAiVLChatModel implements ChatModel {
    private final DashScopeAiApi dashScopeAiApi;

    public DashScopeAiVLChatModel(DashScopeAiApi dashScopeAiApi) {
        this.dashScopeAiApi = dashScopeAiApi;
    }

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
                    content.addAll(message.getMedia().stream().map(media -> Map.of("image", media.getData())).toList());
                    MultiModalMessage build = MultiModalMessage.builder().role(message.getMessageType().getValue())
                            .content(content)
                            .build();
                    return build;
                })
                .toList();
        var builder = MultiModalConversationParam.builder()
                .messages(modalMessages);

        if (prompt.getOptions() != null) {
            DashScopeAiChatOptions options = (DashScopeAiChatOptions) prompt.getOptions();
            if (StringUtils.hasText(options.getModel())) {
                builder.model(options.getModel());
            }
            if (options.getTopK() != null) {
                builder.topK(options.getTopK());
            }
            if (options.getMaxTokens() != null) {
                builder.maxLength(options.getMaxTokens());
            }
        }
        return builder.build();
    }

    public ChatResponse toResponse(MultiModalConversationResult result) {
        List<Generation> generations = result.getOutput()
                .getChoices()
                .stream()
                .map(choice -> {
                    return new Generation((String) choice.getMessage().getContent().get(0).get("text"))
                            .withGenerationMetadata(ChatGenerationMetadata.from(choice.getFinishReason(), null));
                })
                .toList();

        ChatResponseMetadata chatResponseMetadata = new ChatResponseMetadata.DefaultChatResponseMetadata() {
            @Override
            public Usage getUsage() {
                return new Usage() {
                    @Override
                    public Long getPromptTokens() {
                        return result.getUsage().getInputTokens().longValue();
                    }

                    @Override
                    public Long getGenerationTokens() {
                        return result.getUsage().getOutputTokens().longValue();
                    }
                };
            }
        };
        return new ChatResponse(generations, chatResponseMetadata);
    }
}
