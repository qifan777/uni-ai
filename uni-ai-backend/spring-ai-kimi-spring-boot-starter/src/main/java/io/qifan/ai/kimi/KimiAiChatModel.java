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
import org.springframework.ai.model.ModelOptions;
import org.springframework.ai.model.ModelOptionsUtils;
import org.springframework.ai.model.function.AbstractFunctionCallSupport;
import org.springframework.ai.model.function.FunctionCallbackContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class KimiAiChatModel extends AbstractFunctionCallSupport<KimiAiApi.ChatCompletionMessage, KimiAiApi.ChatCompletionRequest, ResponseEntity<KimiAiApi.ChatCompletion>> implements ChatModel {
    private final KimiAiApi kimiAiApi;
    private final KimiAiChatOptions defaultOptions;
    private final RetryTemplate retryTemplate;

    public KimiAiChatModel(FunctionCallbackContext functionCallbackContext, KimiAiApi kimiAiApi, KimiAiChatOptions defaultOptions, RetryTemplate retryTemplate) {
        super(functionCallbackContext);
        this.kimiAiApi = kimiAiApi;
        this.defaultOptions = defaultOptions;
        this.retryTemplate = retryTemplate;
    }

    @Override
    public ChatResponse call(Prompt prompt) {
        KimiAiApi.ChatCompletionRequest request = this.createRequest(prompt, false);
        return (ChatResponse) this.retryTemplate.execute((ctx) -> {
            ResponseEntity completionEntity = this.callWithFunctionSupport(request);
            KimiAiApi.ChatCompletion chatCompletion = (KimiAiApi.ChatCompletion) completionEntity.getBody();
            if (chatCompletion == null) {
                log.warn("No chat completion returned for prompt: {}", prompt);
                return new ChatResponse(List.of());
            } else {

                List<KimiAiApi.ChatCompletion.Choice> choices = chatCompletion.choices();
                if (choices == null) {
                    log.warn("No choices returned for prompt: {}", prompt);
                    return new ChatResponse(List.of());
                } else {
                    List<Generation> generations = choices.stream().map((choice) -> {
                        return (new Generation(choice.message().content(), this.toMap(chatCompletion.id(), choice))).withGenerationMetadata(ChatGenerationMetadata.from(choice.finishReason().name(), (Object) null));
                    }).toList();
                    return new ChatResponse(generations, toMetadata(chatCompletion));
                }
            }
        });
    }

    private Map<String, Object> toMap(String id, KimiAiApi.ChatCompletion.Choice choice) {
        Map<String, Object> map = new HashMap<>();
        KimiAiApi.ChatCompletionMessage message = choice.message();
        if (message.role() != null) {
            map.put("role", message.role().name());
        }

        if (choice.finishReason() != null) {
            map.put("finishReason", choice.finishReason().name());
        }
        map.put("id", id);
        return map;
    }

    private ChatResponseMetadata toMetadata(KimiAiApi.ChatCompletion chatCompletion) {
        return new ChatResponseMetadata.DefaultChatResponseMetadata() {
            @Override
            public Usage getUsage() {
                return new Usage() {
                    @Override
                    public Long getPromptTokens() {
                        return chatCompletion.usage().promptTokens();
                    }

                    @Override
                    public Long getGenerationTokens() {
                        return chatCompletion.usage().completionTokens();
                    }
                };
            }
        };

    }


    @Override
    public ChatOptions getDefaultOptions() {
        return KimiAiChatOptions.fromOptions(defaultOptions);
    }

    @Override
    public Flux<ChatResponse> stream(Prompt prompt) {
        KimiAiApi.ChatCompletionRequest request = this.createRequest(prompt, true);
        return this.retryTemplate.execute((ctx) -> {
            Flux<KimiAiApi.ChatCompletionChunk> completionChunks = this.kimiAiApi.chatCompletionStream(request);
            ConcurrentHashMap<String, String> roleMap = new ConcurrentHashMap<>();
            return completionChunks
                    .map(this::chunkToChatCompletion)
                    .switchMap((cc) -> {
                        return this.handleFunctionCallOrReturnStream(request, Flux.just(ResponseEntity.of(Optional.of(cc))));
                    })
                    .map(HttpEntity::getBody)
                    .map((chatCompletion) -> {
                        try {
                            String id = chatCompletion.id();
                            List<Generation> generations = chatCompletion.choices().stream().map((choice) -> {
                                if (choice.message().role() != null) {
                                    roleMap.putIfAbsent(id, choice.message().role().name());
                                }

                                String finish = choice.finishReason() != null ? choice.finishReason().name() : "";
                                Generation generation = new Generation(choice.message().content(), Map.of("id", id, "role", roleMap.get(id), "finishReason", finish));
                                if (choice.finishReason() != null) {
                                    generation = generation.withGenerationMetadata(ChatGenerationMetadata.from(choice.finishReason().name(), (Object) null));
                                }

                                return generation;
                            }).toList();
                            return new ChatResponse(generations, toMetadata(chatCompletion));
                        } catch (Exception var4) {
                            log.error("Error processing chat completion", var4);
                            return new ChatResponse(List.of());
                        }
                    });
        });
    }

    private KimiAiApi.ChatCompletion chunkToChatCompletion(KimiAiApi.ChatCompletionChunk chunk) {
        List<KimiAiApi.ChatCompletion.Choice> choices = chunk.choices().stream().map((cc) -> {
            return new KimiAiApi.ChatCompletion.Choice(cc.finishReason(), cc.index(), cc.delta());
        }).toList();
        return new KimiAiApi.ChatCompletion(chunk.id(), choices, chunk.created(), chunk.model(), chunk.systemFingerprint(), "chat.completion", chunk.choices().get(0).usage());
    }


    KimiAiApi.ChatCompletionRequest createRequest(Prompt prompt, boolean stream) {
        Set<String> functionsForThisRequest = new HashSet<>();
        List<KimiAiApi.ChatCompletionMessage> chatCompletionMessages = prompt.getInstructions().stream().map((m) -> {
            List<KimiAiApi.ChatCompletionMessage.MediaContent> contents = new ArrayList<>(List.of(new KimiAiApi.ChatCompletionMessage.MediaContent(m.getContent())));

            return new KimiAiApi.ChatCompletionMessage(contents, KimiAiApi.ChatCompletionMessage.Role.valueOf(m.getMessageType().name()));
        }).toList();
        KimiAiApi.ChatCompletionRequest request = new KimiAiApi.ChatCompletionRequest(chatCompletionMessages, stream);
        if (prompt.getOptions() != null) {
            ModelOptions var7 = prompt.getOptions();
            if (!(var7 instanceof ChatOptions runtimeOptions)) {
                throw new IllegalArgumentException("Prompt options are not of type ChatOptions: " + prompt.getOptions().getClass().getSimpleName());
            }

            KimiAiChatOptions updatedRuntimeOptions = ModelOptionsUtils.copyToTarget(runtimeOptions, ChatOptions.class, KimiAiChatOptions.class);
            Set<String> promptEnabledFunctions = this.handleFunctionCallbackConfigurations(updatedRuntimeOptions, true);
            functionsForThisRequest.addAll(promptEnabledFunctions);
            request = ModelOptionsUtils.merge(updatedRuntimeOptions, request, KimiAiApi.ChatCompletionRequest.class);
        }

        if (this.defaultOptions != null) {
            Set<String> defaultEnabledFunctions = this.handleFunctionCallbackConfigurations(this.defaultOptions, false);
            functionsForThisRequest.addAll(defaultEnabledFunctions);
            request = ModelOptionsUtils.merge(request, this.defaultOptions, KimiAiApi.ChatCompletionRequest.class);
        }

        if (!CollectionUtils.isEmpty(functionsForThisRequest)) {
            request = ModelOptionsUtils.merge(new KimiAiChatOptions().setTools(this.getFunctionTools(functionsForThisRequest)), request, KimiAiApi.ChatCompletionRequest.class);
        }

        return request;
    }

    private List<KimiAiApi.FunctionTool> getFunctionTools(Set<String> functionNames) {
        return this.resolveFunctionCallbacks(functionNames).stream().map((functionCallback) -> {
            KimiAiApi.FunctionTool.Function function = new KimiAiApi.FunctionTool.Function(functionCallback.getDescription(), functionCallback.getName(), functionCallback.getInputTypeSchema());
            return new KimiAiApi.FunctionTool(function);
        }).toList();
    }


    @Override
    protected KimiAiApi.ChatCompletionRequest doCreateToolResponseRequest(KimiAiApi.ChatCompletionRequest previousRequest, KimiAiApi.ChatCompletionMessage responseMessage, List<KimiAiApi.ChatCompletionMessage> conversationHistory) {
        for (KimiAiApi.ChatCompletionMessage.ToolCall toolCall : responseMessage.toolCalls()) {
            String functionName = toolCall.function().name();
            String functionArguments = toolCall.function().arguments();
            if (!this.functionCallbackRegister.containsKey(functionName)) {
                throw new IllegalStateException("No function callback found for function name: " + functionName);
            }

            String functionResponse = this.functionCallbackRegister.get(functionName).call(functionArguments);
            conversationHistory.add(new KimiAiApi.ChatCompletionMessage(functionResponse, KimiAiApi.ChatCompletionMessage.Role.TOOL, functionName, toolCall.id(), null));
        }

        KimiAiApi.ChatCompletionRequest newRequest = new KimiAiApi.ChatCompletionRequest(conversationHistory, previousRequest.stream());
        newRequest = ModelOptionsUtils.merge(newRequest, previousRequest, KimiAiApi.ChatCompletionRequest.class);
        return newRequest;
    }

    @Override
    protected List<KimiAiApi.ChatCompletionMessage> doGetUserMessages(KimiAiApi.ChatCompletionRequest request) {
        return request.messages();
    }

    @Override
    protected KimiAiApi.ChatCompletionMessage doGetToolResponseMessage(ResponseEntity<KimiAiApi.ChatCompletion> response) {
        return response.getBody().choices().iterator().next().message();
    }

    @Override
    protected ResponseEntity<KimiAiApi.ChatCompletion> doChatCompletion(KimiAiApi.ChatCompletionRequest request) {
        return this.kimiAiApi.chatCompletionEntity(request);
    }

    @Override
    protected Flux<ResponseEntity<KimiAiApi.ChatCompletion>> doChatCompletionStream(KimiAiApi.ChatCompletionRequest request) {
        return this.kimiAiApi.chatCompletionStream(request).map(this::chunkToChatCompletion).map(Optional::ofNullable).map(ResponseEntity::of);
    }


    @Override
    protected boolean isToolFunctionCall(ResponseEntity<KimiAiApi.ChatCompletion> response) {
        KimiAiApi.ChatCompletion body = response.getBody();
        if (body == null) {
            return false;
        } else {
            List<KimiAiApi.ChatCompletion.Choice> choices = body.choices();
            if (CollectionUtils.isEmpty(choices)) {
                return false;
            } else {
                KimiAiApi.ChatCompletion.Choice choice = choices.get(0);
                return !CollectionUtils.isEmpty(choice.message().toolCalls()) && choice.finishReason() == KimiAiApi.ChatCompletionFinishReason.TOOL_CALLS;
            }
        }

    }


}
