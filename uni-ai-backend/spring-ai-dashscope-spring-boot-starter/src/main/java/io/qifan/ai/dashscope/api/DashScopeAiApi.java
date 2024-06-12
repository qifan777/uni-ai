package io.qifan.ai.dashscope.api;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationParam;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.embeddings.TextEmbedding;
import com.alibaba.dashscope.embeddings.TextEmbeddingParam;
import com.alibaba.dashscope.embeddings.TextEmbeddingResult;
import com.alibaba.dashscope.utils.Constants;
import io.reactivex.Flowable;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class DashScopeAiApi {

    public DashScopeAiApi(String apiKey) {
        Constants.apiKey = apiKey;
    }

    @SneakyThrows
    public GenerationResult chatCompletion(GenerationParam generationParam) {
        Generation gen = new Generation();
        return gen.call(generationParam);
    }

    @SneakyThrows
    public Flux<GenerationResult> chatCompletionStream(GenerationParam generationParam) {
        Generation gen = new Generation();
        Flowable<GenerationResult> stream = gen.streamCall(generationParam);
        AtomicBoolean isInsideTool = new AtomicBoolean(false);
        return Flux.<GenerationResult>create(fluxSink -> {
                    stream.subscribe(result -> {
                        fluxSink.next(result);
                        String finishReason = result.getOutput().getChoices().get(0).getFinishReason();
                        if (StringUtils.hasLength(finishReason) && !finishReason.equalsIgnoreCase("null")) {
                            fluxSink.complete();
                        }
                    });
                })
                .map(result -> {
                    if (isToolFunctionCall(result)) {
                        isInsideTool.set(true);
                    }
                    return result;
                })
                .windowUntil(
                        result -> {
                            if (isInsideTool.get() && isToolFunctionCallFinish(result)) {
                                isInsideTool.set(false);
                                return true;
                            }
                            return !isInsideTool.get();
                        }
                )
                .concatMap(Flux::last);
    }

    @SneakyThrows
    public MultiModalConversationResult vlChatCompletion(MultiModalConversationParam param) {
        MultiModalConversation conv = new MultiModalConversation();
        return conv.call(param);
    }

    @SneakyThrows
    public Flowable<MultiModalConversationResult> vlChatCompletionStream(MultiModalConversationParam param) {
        MultiModalConversation conv = new MultiModalConversation();
        return conv.streamCall(param);
    }

    @SneakyThrows
    public TextEmbeddingResult embedding(TextEmbeddingParam embeddingParam) {
        TextEmbedding textEmbedding = new TextEmbedding();
        return textEmbedding.call(embeddingParam);
    }

    public Boolean isToolFunctionCall(GenerationResult result) {
        return result.getOutput() != null && !CollectionUtils.isEmpty(result.getOutput().getChoices())
                && !CollectionUtils.isEmpty(result.getOutput().getChoices().get(0).getMessage().getToolCalls());
    }

    public Boolean isToolFunctionCallFinish(GenerationResult result) {
        String finishReason = result.getOutput().getChoices().get(0).getFinishReason();
        return isToolFunctionCall(result) && finishReason != null && finishReason.equalsIgnoreCase("tool_calls");
    }
}
