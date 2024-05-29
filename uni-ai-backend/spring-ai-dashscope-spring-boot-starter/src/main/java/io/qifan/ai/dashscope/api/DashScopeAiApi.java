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

@Slf4j
public class DashScopeAiApi {

    public DashScopeAiApi(String apiKey) {
        Constants.apiKey = apiKey;
    }

    @SneakyThrows
    public GenerationResult chat(GenerationParam generationParam) {
        Generation gen = new Generation();
        return gen.call(generationParam);
    }

    @SneakyThrows
    public Flowable<GenerationResult> stream(GenerationParam generationParam) {
        Generation gen = new Generation();
        return gen.streamCall(generationParam);

    }

    @SneakyThrows
    public Flowable<MultiModalConversationResult> vlStream(MultiModalConversationParam param) {
        MultiModalConversation conv = new MultiModalConversation();
        return conv.streamCall(param);
    }

    @SneakyThrows
    public TextEmbeddingResult embedding(TextEmbeddingParam embeddingParam) {
        TextEmbedding textEmbedding = new TextEmbedding();
        return textEmbedding.call(embeddingParam);
    }
}
