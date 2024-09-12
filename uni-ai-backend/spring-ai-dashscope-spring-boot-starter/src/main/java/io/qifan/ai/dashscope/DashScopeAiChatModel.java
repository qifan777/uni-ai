package io.qifan.ai.dashscope;

import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.common.*;
import com.alibaba.dashscope.tools.*;
import com.google.gson.JsonParser;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.metadata.ChatGenerationMetadata;
import org.springframework.ai.chat.metadata.ChatResponseMetadata;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.*;
import org.springframework.ai.chat.observation.ChatModelObservationContext;
import org.springframework.ai.chat.observation.ChatModelObservationConvention;
import org.springframework.ai.chat.observation.ChatModelObservationDocumentation;
import org.springframework.ai.chat.observation.DefaultChatModelObservationConvention;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DashScopeAiChatModel extends AbstractToolCallSupport implements ChatModel {
    private static final ChatModelObservationConvention DEFAULT_OBSERVATION_CONVENTION = new DefaultChatModelObservationConvention();
    private final DashScopeAiApi dashScopeAiApi;
    private final DashScopeAiChatOptions defaultOptions;
    @Setter
    @Getter
    private ChatModelObservationConvention observationConvention;
    private final ObservationRegistry observationRegistry;
    private final RetryTemplate retryTemplate;

    public DashScopeAiChatModel(DashScopeAiApi dashScopeAiApi, DashScopeAiChatOptions defaultOptions) {
        this(null, dashScopeAiApi, defaultOptions, RetryUtils.DEFAULT_RETRY_TEMPLATE, ObservationRegistry.NOOP);
    }

    public DashScopeAiChatModel(FunctionCallbackContext functionCallbackContext, DashScopeAiApi dashScopeAiApi, DashScopeAiChatOptions defaultOptions) {
        this(functionCallbackContext, dashScopeAiApi, defaultOptions, RetryUtils.DEFAULT_RETRY_TEMPLATE, ObservationRegistry.NOOP);

    }


    public DashScopeAiChatModel(FunctionCallbackContext functionCallbackContext, DashScopeAiApi dashScopeAiApi, DashScopeAiChatOptions defaultOptions, RetryTemplate retryTemplate, ObservationRegistry observationRegistry) {
        super(functionCallbackContext);
        this.dashScopeAiApi = dashScopeAiApi;
        this.defaultOptions = defaultOptions;
        this.observationRegistry = observationRegistry;
        this.retryTemplate = retryTemplate;
        observationConvention = DEFAULT_OBSERVATION_CONVENTION;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        GenerationParam request = this.createRequest(prompt);
        ChatModelObservationContext observationContext = ChatModelObservationContext.builder()
                .prompt(prompt)
                .provider("DASH_SCOPE")
                .requestOptions(this.buildRequestOptions(request))
                .build();
        ChatResponse response = ChatModelObservationDocumentation.CHAT_MODEL_OPERATION
                .observation(this.observationConvention, DEFAULT_OBSERVATION_CONVENTION, () -> observationContext, this.observationRegistry)
                .observe(() -> {
                    GenerationResult generationResult = this.retryTemplate.execute((ctx) -> this.dashScopeAiApi.chatCompletion(request));
                    if (generationResult == null) {
                        log.warn("No chat completion returned for prompt: {}", prompt);
                        return new ChatResponse(List.of());
                    } else {
                        GenerationOutput chatCompletion = generationResult.getOutput();
                        List<GenerationOutput.Choice> choices = chatCompletion.getChoices();
                        if (choices == null) {
                            log.warn("No choices returned for prompt: {}", prompt);
                            return new ChatResponse(List.of());
                        } else {
                            List<Generation> generations = choices.stream().map((choice) -> {
                                Map<String, Object> metadata = Map.of("id", generationResult.getRequestId() != null ? generationResult.getRequestId() : "", "role", choice.getMessage().getRole() != null ? choice.getMessage().getRole() : "", "index", choice.getIndex() != null ? choice.getIndex() : "", "finishReason", choice.getFinishReason() != null ? choice.getFinishReason() : "");
                                return this.buildGeneration(choice, metadata);
                            }).toList();
                            ChatResponse chatResponse = new ChatResponse(generations, this.from(generationResult));
                            observationContext.setResponse(chatResponse);
                            return chatResponse;
                        }
                    }
                });
        if (response != null && this.isToolCall(response, Set.of("tool_calls", "stop"))) {
            List<org.springframework.ai.chat.messages.Message> toolCallConversation = this.handleToolCalls(prompt, response);
            return this.call(new Prompt(toolCallConversation, prompt.getOptions()));
        } else {
            return response;
        }
    }

    private ChatResponseMetadata from(GenerationResult result) {
        return ChatResponseMetadata.builder().withUsage(new Usage() {
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
    }

    private Generation buildGeneration(GenerationOutput.Choice choice, Map<String, Object> metadata) {
        List<AssistantMessage.ToolCall> toolCalls = choice.getMessage().getToolCalls() == null ? List.of() : choice.getMessage().getToolCalls().stream().map((toolCall) -> {
            ToolCallFunction toolCallFunction = (ToolCallFunction) toolCall;
            return new AssistantMessage.ToolCall(toolCall.getId(), "function", toolCallFunction.getFunction().getName(), toolCallFunction.getFunction().getArguments());
        }).toList();
        AssistantMessage assistantMessage = new AssistantMessage(choice.getMessage().getContent(), metadata, toolCalls);
        String finishReason = choice.getFinishReason() != null ? choice.getFinishReason() : "";
        ChatGenerationMetadata generationMetadata = ChatGenerationMetadata.from(finishReason, null);
        return new Generation(assistantMessage, generationMetadata);
    }

    @Override
    public ChatOptions getDefaultOptions() {
        return DashScopeAiChatOptions.fromOptions(defaultOptions);
    }

    public Flux<ChatResponse> stream(Prompt prompt) {
        return Flux.deferContextual((contextView) -> {
            GenerationParam request = this.createRequest(prompt);
            Flux<GenerationResult> completionChunks = this.dashScopeAiApi.chatCompletionStream(request);
            ConcurrentHashMap<String, String> roleMap = new ConcurrentHashMap<>();
            ChatModelObservationContext observationContext = ChatModelObservationContext.builder().prompt(prompt).provider("DASH_SCOPE").requestOptions(this.buildRequestOptions(request)).build();
            Observation observation = ChatModelObservationDocumentation.CHAT_MODEL_OPERATION.observation(this.observationConvention, DEFAULT_OBSERVATION_CONVENTION, () -> {
                return observationContext;
            }, this.observationRegistry);
            observation.parentObservation((Observation) contextView.getOrDefault("micrometer.observation", (Object) null)).start();
            Flux<ChatResponse> chatResponse = completionChunks.switchMap((chatCompletion) -> {
                return Mono.just(chatCompletion).map((chatCompletion2) -> {
                    try {
                        String id = chatCompletion2.getRequestId();
                        List<Generation> generations = chatCompletion2.getOutput().getChoices().stream().map((choice) -> {
                            if (choice.getMessage().getRole() != null) {
                                roleMap.putIfAbsent(id, choice.getMessage().getRole());
                            }
                            Map<String, Object> metadata = Map.of("id", id, "role", roleMap.getOrDefault(id, ""), "index", choice.getIndex(), "finishReason", choice.getFinishReason() != null ? choice.getFinishReason() : "");
                            return this.buildGeneration(choice, metadata);
                        }).toList();
                        return new ChatResponse(generations, this.from(chatCompletion2));
                    } catch (Exception var5) {
                        log.error("Error processing chat completion", var5);
                        return new ChatResponse(List.of());
                    }
                });
            });
            Flux<ChatResponse> chatResponseFlux = chatResponse.flatMap((response) -> {
                if (this.isToolCall(response, Set.of("tool_calls", "stop"))) {
                    List<org.springframework.ai.chat.messages.Message> toolCallConversation = this.handleToolCalls(prompt, response);
                    return this.stream(new Prompt(toolCallConversation, prompt.getOptions()));
                } else {
                    return Flux.just(response);
                }
            });
            Objects.requireNonNull(observation);
            Flux<ChatResponse> flux = chatResponseFlux.doOnError(observation::error).doFinally((s) -> {
                observation.stop();
            }).contextWrite((ctx) -> ctx.put("micrometer.observation", observation));
            MessageAggregator var10 = new MessageAggregator();
            Objects.requireNonNull(observationContext);
            return var10.aggregate(flux, observationContext::setResponse);
        });
    }


    public GenerationParam createRequest(Prompt prompt) {
        List<Message> chatCompletionMessages = prompt.getInstructions().stream().map((message) -> {
            if (message.getMessageType() != MessageType.USER && message.getMessageType() != MessageType.SYSTEM) {
                if (message.getMessageType() == MessageType.ASSISTANT) {
                    AssistantMessage assistantMessage = (AssistantMessage) message;
                    List<ToolCallBase> toolCalls = null;
                    if (!CollectionUtils.isEmpty(assistantMessage.getToolCalls())) {
                        toolCalls = assistantMessage.getToolCalls().stream().map((toolCall) -> {
                            ToolCallFunction toolCallFunction = new ToolCallFunction();
                            ToolCallFunction.CallFunction callFunction = toolCallFunction.new CallFunction();
                            callFunction.setName(toolCall.name());
                            callFunction.setArguments(toolCall.arguments());
                            toolCallFunction.setType(toolCall.type());
                            toolCallFunction.setId(toolCall.id());
                            toolCallFunction.setFunction(callFunction);
                            return (ToolCallBase) toolCallFunction;
                        }).toList();
                    }
                    return List.of((Message) Message.builder().content(assistantMessage.getContent())
                            .role(Role.ASSISTANT.getValue())
                            .toolCalls(toolCalls).build());
                } else if (message.getMessageType() == MessageType.TOOL) {
                    ToolResponseMessage toolMessage = (ToolResponseMessage) message;
                    toolMessage.getResponses().forEach((response) -> {
                        Assert.isTrue(response.id() != null, "ToolResponseMessage must have an id");
                        Assert.isTrue(response.name() != null, "ToolResponseMessage must have a name");
                    });
                    return toolMessage.getResponses().stream().map((tr) -> (Message) Message.builder().content(tr.responseData())
                            .role(Role.TOOL.getValue())
                            .name(tr.name())
                            .toolCallId(tr.id()).build()).toList();
                } else {
                    throw new IllegalArgumentException("Unsupported message type: " + message.getMessageType());
                }
            } else {
                Message.MessageBuilder<?, ?> builder = Message.builder();
                if (message instanceof UserMessage userMessage) {
                    String content = message.getContent();
                    if (!CollectionUtils.isEmpty(userMessage.getMedia())) {
                        List<MessageContentBase> contentList = new ArrayList<>(List.of(MessageContentText.builder().text(userMessage.getContent()).build()));
                        contentList.addAll(userMessage.getMedia().stream().map((media) -> (MessageContentImageURL) MessageContentImageURL.builder().imageURL(ImageURL.builder().url((String) media.getData()).build()).build()).toList());
                        builder.contents(contentList);
                    } else {
                        builder.content(content);
                    }
                }
                Message build = builder.role(message.getMessageType().getValue())
                        .build();
                return List.of(build);
            }
        }).flatMap(Collection::stream).toList();
        GenerationParam.GenerationParamBuilder<?, ?> builder = GenerationParam.builder();
        builder
                .messages(chatCompletionMessages)
                .resultFormat(GenerationParam.ResultFormat.MESSAGE);
        DashScopeAiChatOptions options = new DashScopeAiChatOptions();
        Set<String> enabledToolsToUse = new HashSet<>();
        if (defaultOptions != null) {
            options = ModelOptionsUtils.merge(defaultOptions, options, DashScopeAiChatOptions.class);
            enabledToolsToUse.addAll(defaultOptions.getFunctions());
        }
        if (prompt.getOptions() != null) {
            if (!(prompt.getOptions() instanceof DashScopeAiChatOptions)) {
                throw new IllegalArgumentException("DashScopeAiChatOptions is required");
            }
            options = ModelOptionsUtils.merge(prompt.getOptions(), options, DashScopeAiChatOptions.class);
            enabledToolsToUse.addAll(((DashScopeAiChatOptions) prompt.getOptions()).getFunctions());
        }
        options.setFunctions(enabledToolsToUse);
        if (!CollectionUtils.isEmpty(options.getFunctions())) {
            builder.tools(getFunctionTools(options.getFunctions()));
        }
        if (StringUtils.hasText(options.getModel())) {
            builder.model(options.getModel());
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
        if (options.getIncrementalOutput() != null) {
            builder.incrementalOutput(options.getIncrementalOutput());
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
            return (ToolBase) ToolFunction.builder().type("function")
                    .function(functionDefinition).build();
        }).toList();
    }

    public ChatResponse toResponse(GenerationResult result) {
        List<Generation> generations = result.getOutput()
                .getChoices()
                .stream()
                .map(choice -> new Generation(choice.getMessage().getContent())
                        .withGenerationMetadata(ChatGenerationMetadata.from(choice.getFinishReason(), null)))
                .toList();

        ChatResponseMetadata chatResponseMetadata = ChatResponseMetadata.builder().withUsage(new Usage() {
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


    private ChatOptions buildRequestOptions(GenerationParam request) {
        return ChatOptionsBuilder.builder().withModel(request.getModel()).withFrequencyPenalty(request.getRepetitionPenalty()).withMaxTokens(request.getMaxTokens()).withPresencePenalty(null).withStopSequences(request.getStopStrings()).withTemperature(request.getTemperature()).withTopP(request.getTopP().floatValue()).build();
    }

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
