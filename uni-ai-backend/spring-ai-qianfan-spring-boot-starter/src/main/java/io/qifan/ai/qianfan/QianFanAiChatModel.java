package io.qifan.ai.qianfan;

import com.baidubce.qianfan.model.chat.ChatRequest;
import com.baidubce.qianfan.model.chat.Function;
import com.baidubce.qianfan.model.chat.Message;
import com.google.gson.JsonParser;
import io.qifan.ai.qianfan.api.QianFanApi;
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
import org.springframework.ai.model.function.FunctionCallingOptions;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;

public class QianFanAiChatModel extends AbstractFunctionCallSupport<Message, ChatRequest, com.baidubce.qianfan.model.chat.ChatResponse> implements ChatModel {
    private final QianFanApi qianFanApi;
    private final QianFanAiChatOptions defaultOptions;

    public QianFanAiChatModel(FunctionCallbackContext functionCallbackContext, QianFanApi qianFanApi, QianFanAiChatOptions defaultOptions) {
        super(functionCallbackContext);
        this.qianFanApi = qianFanApi;
        this.defaultOptions = defaultOptions;
    }


    @Override
    public ChatResponse call(Prompt prompt) {
        return toResponse(callWithFunctionSupport(toRequest(prompt)));
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return QianFanAiChatOptions.fromOptions(defaultOptions);
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        ChatRequest request = toRequest(prompt);
        return qianFanApi.chatCompletionStream(request)
                .switchMap(generationResult -> handleFunctionCallOrReturnStream(request, Flux.just(generationResult)))
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

        QianFanAiChatOptions options = new QianFanAiChatOptions();
        if (defaultOptions != null) {
            options = ModelOptionsUtils.merge(options, defaultOptions, QianFanAiChatOptions.class);
            Set<String> enabledFunctions = handleFunctionCallbackConfigurations(defaultOptions, false);
            options.setFunctions(enabledFunctions);
        }
        if (prompt.getOptions() != null) {
            if (!(prompt.getOptions() instanceof QianFanAiChatOptions)) {
                throw new IllegalArgumentException("Options must be instance of QianFanAiChatOptions");
            }
            options = ModelOptionsUtils.merge(options, prompt.getOptions(), QianFanAiChatOptions.class);
            Set<String> enabledFunctions = handleFunctionCallbackConfigurations((FunctionCallingOptions) prompt.getOptions(), true);
            options.setFunctions(enabledFunctions);
        }
        ChatRequest chatRequest = new ChatRequest();
        if (options.getTemperature() != null) {
            chatRequest.setTemperature(options.getTemperature().doubleValue());
        }
        if (options.getTopP() != null) {
            chatRequest.setTopP(options.getTopP().doubleValue());
        }
        if (options.getMaxTokens() != null) {
            chatRequest.setMaxOutputTokens(options.getMaxTokens());
        }
        if (options.getModel() != null) {
            chatRequest.setModel(options.getModel());
        }
        if (!CollectionUtils.isEmpty(options.getFunctions())) {
            chatRequest.setFunctions(getFunctionTools(options.getFunctions()));
        }
        chatRequest.setMessages(messages);
        return chatRequest;
    }

    private List<Function> getFunctionTools(Set<String> functionNames) {
        return this.resolveFunctionCallbacks(functionNames).stream().map(functionCallback -> new Function().setDescription(functionCallback.getDescription())
                .setName(functionCallback.getName())
                .setParameters(JsonParser.parseString(functionCallback.getInputTypeSchema()).getAsJsonObject())).toList();
    }

    @Override
    protected ChatRequest doCreateToolResponseRequest(ChatRequest previousRequest, Message responseMessage, List<Message> conversationHistory) {
        String functionName = responseMessage.getFunctionCall().getName();
        String functionArguments = responseMessage.getFunctionCall().getArguments();
        if (!this.functionCallbackRegister.containsKey(functionName)) {
            throw new RuntimeException("No function callback found for function name: " + functionName);

        }
        String functionResponse = this.functionCallbackRegister.get(functionName).call(functionArguments);
        conversationHistory.add(new Message().setRole("function").setName(functionName)
                .setContent(functionResponse));
        previousRequest.setMessages(conversationHistory);
        return previousRequest;
    }

    @Override
    protected List<Message> doGetUserMessages(ChatRequest request) {
        return request.getMessages();
    }

    @Override
    protected Message doGetToolResponseMessage(com.baidubce.qianfan.model.chat.ChatResponse response) {
        return new Message().setRole("assistant")
                .setFunctionCall(response.getFunctionCall());
    }

    @Override
    protected com.baidubce.qianfan.model.chat.ChatResponse doChatCompletion(ChatRequest request) {
        return this.qianFanApi.chatCompletion(request);
    }

    @Override
    protected Flux<com.baidubce.qianfan.model.chat.ChatResponse> doChatCompletionStream(ChatRequest request) {
        return this.qianFanApi.chatCompletionStream(request);
    }

    @Override
    protected boolean isToolFunctionCall(com.baidubce.qianfan.model.chat.ChatResponse response) {
        return response.getFunctionCall() != null;
    }
}
