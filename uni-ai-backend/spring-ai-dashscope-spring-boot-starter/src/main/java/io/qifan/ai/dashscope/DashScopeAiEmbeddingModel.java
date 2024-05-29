package io.qifan.ai.dashscope;

import com.alibaba.dashscope.embeddings.TextEmbeddingParam;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.*;

import java.util.List;

public class DashScopeAiEmbeddingModel implements EmbeddingModel {
    private final DashScopeAiApi dashScopeAiApi;
    private final DashScopeAiEmbeddingOptions embeddingOptions;
    private final MetadataMode metadataMode;

    public DashScopeAiEmbeddingModel(DashScopeAiApi dashScopeAiApi) {
        this.dashScopeAiApi = dashScopeAiApi;
        embeddingOptions = new DashScopeAiEmbeddingOptions();
        metadataMode = MetadataMode.EMBED;
    }

    public DashScopeAiEmbeddingModel(DashScopeAiApi dashScopeAiApi, DashScopeAiEmbeddingOptions dashScopeAiEmbeddingOptions, MetadataMode metadataMode) {
        this.dashScopeAiApi = dashScopeAiApi;
        this.embeddingOptions = dashScopeAiEmbeddingOptions;
        this.metadataMode = metadataMode;
    }

    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        var embeddingParam = TextEmbeddingParam.builder().model(embeddingOptions.getModel())
                .texts(request.getInstructions()).build();
        var textEmbeddingResult = dashScopeAiApi.embedding(embeddingParam);
        List<Embedding> embeddings = textEmbeddingResult.getOutput().getEmbeddings().stream().map(e -> new Embedding(e.getEmbedding(), e.getTextIndex()))
                .toList();
        var metadata = new EmbeddingResponseMetadata();
        metadata.put("total-tokens", textEmbeddingResult.getUsage().getTotalTokens());
        metadata.put("model", embeddingOptions.getModel());
        return new EmbeddingResponse(embeddings, metadata);
    }

    @Override
    public List<Double> embed(Document document) {
        return this.embed(document.getFormattedContent(this.metadataMode));
    }

}
