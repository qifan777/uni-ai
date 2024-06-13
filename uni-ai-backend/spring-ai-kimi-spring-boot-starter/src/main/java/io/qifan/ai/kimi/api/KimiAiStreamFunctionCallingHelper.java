package io.qifan.ai.kimi.api;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class KimiAiStreamFunctionCallingHelper {
    public KimiAiStreamFunctionCallingHelper() {
    }

    public KimiAiApi.ChatCompletionChunk merge(KimiAiApi.ChatCompletionChunk previous, KimiAiApi.ChatCompletionChunk current) {
        if (previous == null) {
            return current;
        } else {
            String id = current.id() != null ? current.id() : previous.id();
            Long created = current.created() != null ? current.created() : previous.created();
            String model = current.model() != null ? current.model() : previous.model();
            String systemFingerprint = current.systemFingerprint() != null ? current.systemFingerprint() : previous.systemFingerprint();
            String object = current.object() != null ? current.object() : previous.object();
            KimiAiApi.ChatCompletionChunk.ChunkChoice previousChoice0 = CollectionUtils.isEmpty(previous.choices()) ? null : (KimiAiApi.ChatCompletionChunk.ChunkChoice) previous.choices().get(0);
            KimiAiApi.ChatCompletionChunk.ChunkChoice currentChoice0 = CollectionUtils.isEmpty(current.choices()) ? null : (KimiAiApi.ChatCompletionChunk.ChunkChoice) current.choices().get(0);
            KimiAiApi.ChatCompletionChunk.ChunkChoice choice = this.merge(previousChoice0, currentChoice0);
            List<KimiAiApi.ChatCompletionChunk.ChunkChoice> chunkChoices = choice == null ? List.of() : List.of(choice);
            return new KimiAiApi.ChatCompletionChunk(id, chunkChoices, created, model, systemFingerprint, object);
        }
    }

    private KimiAiApi.ChatCompletionChunk.ChunkChoice merge(KimiAiApi.ChatCompletionChunk.ChunkChoice previous, KimiAiApi.ChatCompletionChunk.ChunkChoice current) {
        if (previous == null) {
            return current;
        } else {
            KimiAiApi.ChatCompletionFinishReason finishReason = current.finishReason() != null ? current.finishReason() : previous.finishReason();
            Integer index = current.index() != null ? current.index() : previous.index();
            KimiAiApi.ChatCompletionMessage message = this.merge(previous.delta(), current.delta());
            KimiAiApi.Usage usage = current.usage() != null ? current.usage() : previous.usage();
            return new KimiAiApi.ChatCompletionChunk.ChunkChoice(finishReason, index, message, usage);
        }
    }

    private KimiAiApi.ChatCompletionMessage merge(KimiAiApi.ChatCompletionMessage previous, KimiAiApi.ChatCompletionMessage current) {
        String content = current.content() != null ? current.content() : "" + (previous.content() != null ? previous.content() : "");
        KimiAiApi.ChatCompletionMessage.Role role = current.role() != null ? current.role() : previous.role();
        role = role != null ? role : KimiAiApi.ChatCompletionMessage.Role.ASSISTANT;
        String name = current.name() != null ? current.name() : previous.name();
        String toolCallId = current.toolCallId() != null ? current.toolCallId() : previous.toolCallId();
        List<KimiAiApi.ChatCompletionMessage.ToolCall> toolCalls = new ArrayList();
        KimiAiApi.ChatCompletionMessage.ToolCall lastPreviousTooCall = null;
        if (previous.toolCalls() != null) {
            lastPreviousTooCall = (KimiAiApi.ChatCompletionMessage.ToolCall) previous.toolCalls().get(previous.toolCalls().size() - 1);
            if (previous.toolCalls().size() > 1) {
                toolCalls.addAll(previous.toolCalls().subList(0, previous.toolCalls().size() - 1));
            }
        }

        if (current.toolCalls() != null) {
            if (current.toolCalls().size() > 1) {
                throw new IllegalStateException("Currently only one tool call is supported per message!");
            }

            KimiAiApi.ChatCompletionMessage.ToolCall currentToolCall = (KimiAiApi.ChatCompletionMessage.ToolCall) current.toolCalls().iterator().next();
            if (currentToolCall.id() != null) {
                if (lastPreviousTooCall != null) {
                    toolCalls.add(lastPreviousTooCall);
                }

                toolCalls.add(currentToolCall);
            } else {
                toolCalls.add(this.merge(lastPreviousTooCall, currentToolCall));
            }
        } else if (lastPreviousTooCall != null) {
            toolCalls.add(lastPreviousTooCall);
        }

        return new KimiAiApi.ChatCompletionMessage(content, role, name, toolCallId, toolCalls);
    }

    private KimiAiApi.ChatCompletionMessage.ToolCall merge(KimiAiApi.ChatCompletionMessage.ToolCall previous, KimiAiApi.ChatCompletionMessage.ToolCall current) {
        if (previous == null) {
            return current;
        } else {
            String id = current.id() != null ? current.id() : previous.id();
            String type = current.type() != null ? current.type() : previous.type();
            KimiAiApi.ChatCompletionMessage.ChatCompletionFunction function = this.merge(previous.function(), current.function());
            return new KimiAiApi.ChatCompletionMessage.ToolCall(id, type, function);
        }
    }

    private KimiAiApi.ChatCompletionMessage.ChatCompletionFunction merge(KimiAiApi.ChatCompletionMessage.ChatCompletionFunction previous, KimiAiApi.ChatCompletionMessage.ChatCompletionFunction current) {
        if (previous == null) {
            return current;
        } else {
            String name = current.name() != null ? current.name() : previous.name();
            StringBuilder arguments = new StringBuilder();
            if (previous.arguments() != null) {
                arguments.append(previous.arguments());
            }

            if (current.arguments() != null) {
                arguments.append(current.arguments());
            }

            return new KimiAiApi.ChatCompletionMessage.ChatCompletionFunction(name, arguments.toString());
        }
    }

    public boolean isStreamingToolFunctionCall(KimiAiApi.ChatCompletionChunk chatCompletion) {
        if (chatCompletion != null && !CollectionUtils.isEmpty(chatCompletion.choices())) {
            KimiAiApi.ChatCompletionChunk.ChunkChoice choice = chatCompletion.choices().get(0);
            if (choice != null && choice.delta() != null) {
                return !CollectionUtils.isEmpty(choice.delta().toolCalls());
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isStreamingToolFunctionCallFinish(KimiAiApi.ChatCompletionChunk chatCompletion) {
        if (chatCompletion != null && !CollectionUtils.isEmpty(chatCompletion.choices())) {
            KimiAiApi.ChatCompletionChunk.ChunkChoice choice = chatCompletion.choices().get(0);
            if (choice != null && choice.delta() != null) {
                return choice.finishReason() == KimiAiApi.ChatCompletionFinishReason.TOOL_CALLS;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public KimiAiApi.ChatCompletion chunkToChatCompletion(KimiAiApi.ChatCompletionChunk chunk) {
        List<KimiAiApi.ChatCompletion.Choice> choices = chunk
                .choices()
                .stream()
                .map((chunkChoice) -> new KimiAiApi.ChatCompletion.Choice(chunkChoice.finishReason(), chunkChoice.index(), chunkChoice.delta()))
                .toList();
        return new KimiAiApi.ChatCompletion(chunk.id(), choices, chunk.created(), chunk.model(), chunk.systemFingerprint(), "chat.completion", chunk.choices().get(0).usage());
    }
}