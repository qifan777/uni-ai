package io.qifan.ai.dashscope;

import com.alibaba.dashscope.embeddings.TextEmbeddingParam;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.*;

import java.util.List;

public class DashScopeAiEmbeddingModel implements EmbeddingModel {
    private final DashScopeAiApi dashScopeAiApi;
    private final MetadataMode metadataMode;
    private DashScopeAiEmbeddingOptions options;

    public DashScopeAiEmbeddingModel(DashScopeAiApi dashScopeAiApi, DashScopeAiEmbeddingOptions options) {
        this.dashScopeAiApi = dashScopeAiApi;
        this.options = options;
        metadataMode = MetadataMode.EMBED;
    }

    public DashScopeAiEmbeddingModel(DashScopeAiApi dashScopeAiApi, MetadataMode metadataMode, DashScopeAiEmbeddingOptions options) {
        this.dashScopeAiApi = dashScopeAiApi;
        this.metadataMode = metadataMode;
        this.options = options;
    }

    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        EmbeddingOptions embeddingOptions = request.getOptions();
        if (embeddingOptions instanceof DashScopeAiEmbeddingOptions) {
            options = (DashScopeAiEmbeddingOptions) embeddingOptions;
        }
        var embeddingParam = TextEmbeddingParam.builder().model(options.getModel())
                .texts(request.getInstructions()).build();
        var textEmbeddingResult = dashScopeAiApi.embedding(embeddingParam);
        List<Embedding> embeddings = textEmbeddingResult.getOutput().getEmbeddings().stream().map(e -> new Embedding(e.getEmbedding(), e.getTextIndex()))
                .toList();
        var metadata = new EmbeddingResponseMetadata();
        metadata.put("totalTokens", textEmbeddingResult.getUsage().getTotalTokens());
        return new EmbeddingResponse(embeddings, metadata);
    }

    @Override
    public List<Double> embed(Document document) {
        return this.embed(document.getFormattedContent(this.metadataMode));
    }

}
