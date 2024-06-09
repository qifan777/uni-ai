package io.qifan.ai.dashscope;

import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.tools.*;
import com.google.gson.JsonParser;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.AbstractFunctionCallSupport;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class DashScopeAiChatModel extends AbstractFunctionCallSupport<Message, GenerationParam, GenerationResult> implements ChatModel {
    private final DashScopeAiApi dashScopeAiApi;
    private final DashScopeAiChatOptions defaultOptions;

    public DashScopeAiChatModel(DashScopeAiApi dashScopeAiApi, DashScopeAiChatOptions defaultOptions) {
        this(null, dashScopeAiApi, defaultOptions);
    }

    public DashScopeAiChatModel(FunctionCallbackContext functionCallbackContext, DashScopeAiApi dashScopeAiApi, DashScopeAiChatOptions defaultOptions) {
        super(functionCallbackContext);
        this.dashScopeAiApi = dashScopeAiApi;
        this.defaultOptions = defaultOptions;
    }

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
        Flux<GenerationResult> stream = dashScopeAiApi.chatCompletionStream(params);
        return stream
                .switchMap(generationResult -> handleFunctionCallOrReturnStream(params, Flux.just(generationResult)))
                .map(this::toResponse);
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
            if (!CollectionUtils.isEmpty(options.getFunctions())) {
                builder.tools(getFunctionTools(new HashSet<>(options.getFunctions())));
            }
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

    private List<ToolBase> getFunctionTools(Set<String> functionNames) {
        return this.resolveFunctionCallbacks(functionNames).stream().map(functionCallback -> {
            FunctionDefinition functionDefinition = FunctionDefinition.builder()
                    .name(functionCallback.getName())
                    .description(functionCallback.getDescription())
                    .parameters(JsonParser.parseString(functionCallback.getInputTypeSchema()).getAsJsonObject()).build();
            functionDefinition.getParameters().remove("$schema");
            functionDefinition.getParameters().remove("description");
            ToolBase function = ToolFunction.builder().type("function")
                    .function(functionDefinition).build();
            return function;
        }).toList();
    }

    public ChatResponse toResponse(GenerationResult result) {
        List<Generation> generations = result.getOutput()
                .getChoices()
                .stream()
                .map(choice -> new Generation(choice.getMessage().getContent())
                        .withGenerationMetadata(ChatGenerationMetadata.from(choice.getFinishReason(), null)))
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

    @Override
    protected GenerationParam doCreateToolResponseRequest(GenerationParam previousRequest, Message responseMessage, List<Message> conversationHistory) {
        for (ToolCallBase toolCall : responseMessage.getToolCalls()) {
            if (toolCall instanceof ToolCallFunction toolCallFunction) {
                String functionName = toolCallFunction.getFunction().getName();
                String functionArguments = toolCallFunction.getFunction().getArguments();
                if (!this.functionCallbackRegister.containsKey(functionName)) {
                    throw new RuntimeException("No function callback found for function name: " + functionName);
                }
                String functionResponse = this.functionCallbackRegister.get(functionName).call(functionArguments);
                conversationHistory.add(Message.builder().role("tool").content(functionResponse)
                        .toolCallId(toolCall.getId()).build());
            }
        }
        previousRequest.setMessages(conversationHistory);
        return previousRequest;
    }

    @Override
    protected List<Message> doGetUserMessages(GenerationParam request) {
        return request.getMessages();
    }

    @Override
    protected Message doGetToolResponseMessage(GenerationResult response) {
        return response.getOutput().getChoices().get(0).getMessage();
    }

    @Override
    protected GenerationResult doChatCompletion(GenerationParam request) {
        return dashScopeAiApi.chatCompletion(request);
    }

    @Override
    protected Flux<GenerationResult> doChatCompletionStream(GenerationParam request) {
        return this.dashScopeAiApi.chatCompletionStream(request);
    }

    @Override
    protected boolean isToolFunctionCall(GenerationResult response) {
        if (response == null || response.getOutput() == null) {
            return false;
        }
        if (CollectionUtils.isEmpty(response.getOutput().getChoices())) {
            return false;
        }
        GenerationOutput.Choice choice = response.getOutput().getChoices().get(0);
        return !CollectionUtils.isEmpty(choice.getMessage().getToolCalls());
    }
}
