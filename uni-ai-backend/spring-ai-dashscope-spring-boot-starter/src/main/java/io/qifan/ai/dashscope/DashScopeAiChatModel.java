package io.qifan.ai.dashscope;

import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import io.reactivex.Flowable;
import lombok.AllArgsConstructor;
import lombok.val;
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

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class DashScopeAiChatModel implements ChatModel {
    private final DashScopeAiApi dashScopeAiApi;
    private final DashScopeAiChatOptions defaultOptions;

    @Override
    public ChatResponse call(Prompt prompt) {

        return toResponse(dashScopeAiApi.chatCompletion(toParam(prompt)));
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return null;
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        GenerationParam params = toParam(prompt);
        params.setIncrementalOutput(true);
        Flowable<GenerationResult> stream = dashScopeAiApi.chatCompletionStream(params);
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


    public GenerationParam toParam(Prompt prompt) {
        List<Message> list = prompt.getInstructions()
                .stream()
                .map(instruction -> {
                    Message message = Message.builder()
                            .role(instruction.getMessageType().getValue())
                            .content(instruction.getContent())
                            .build();
                    return message;
                })
                .toList();
        GenerationParam.GenerationParamBuilder<?, ?> builder = GenerationParam.builder();
        builder
                .messages(list)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE);
        DashScopeAiChatOptions options = new DashScopeAiChatOptions();
        if (defaultOptions != null) {
            options = ModelOptionsUtils.merge(defaultOptions, options, DashScopeAiChatOptions.class);
        }
        if (prompt.getOptions() != null) {
            options = ModelOptionsUtils.merge(prompt.getOptions(), options, DashScopeAiChatOptions.class);
        }
        if (options != null) {
            builder.tools(options.getTools());
            if (StringUtils.hasText(options.getModel())) {
                builder.model(options.getModel());
            }
            if (options.getTopK() != null) {
                builder.topK(options.getTopK());
            }
            if (options.getMaxTokens() != null) {
                builder.maxTokens(options.getMaxTokens());
            }
            if (options.getTopP() != null) {
                builder.topP(Double.valueOf(options.getTopP()));
            }
            if (options.getTemperature() != null) {
                builder.temperature(options.getTemperature());
            }
        }
        return builder.build();
    }

    public ChatResponse toResponse(GenerationResult result) {
        List<Generation> generations = result.getOutput()
                .getChoices()
                .stream()
                .map(choice -> {
                    val toolCalls = choice.getMessage().getToolCalls();
                    return new Generation(choice.getMessage().getContent(), Map.of("toolCalls", toolCalls == null ? List.of() : toolCalls))
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
