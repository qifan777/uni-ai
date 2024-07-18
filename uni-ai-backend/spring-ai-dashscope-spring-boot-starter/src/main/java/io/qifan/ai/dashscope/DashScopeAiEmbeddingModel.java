package io.qifan.ai.dashscope;

import com.alibaba.dashscope.embeddings.TextEmbeddingParam;
import io.qifan.ai.dashscope.api.DashScopeAiApi;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.*;
import org.springframework.ai.model.ModelOptionsUtils;

import java.util.List;

public class DashScopeAiEmbeddingModel implements EmbeddingModel {
    private final DashScopeAiApi dashScopeAiApi;
    private final MetadataMode metadataMode;
    private final DashScopeAiEmbeddingOptions defaultOptions;

    public DashScopeAiEmbeddingModel(DashScopeAiApi dashScopeAiApi, DashScopeAiEmbeddingOptions defaultOptions) {
        this.dashScopeAiApi = dashScopeAiApi;
        this.defaultOptions = defaultOptions;
        metadataMode = MetadataMode.EMBED;
    }

    public DashScopeAiEmbeddingModel(DashScopeAiApi dashScopeAiApi, MetadataMode metadataMode, DashScopeAiEmbeddingOptions defaultOptions) {
        this.dashScopeAiApi = dashScopeAiApi;
        this.metadataMode = metadataMode;
        this.defaultOptions = defaultOptions;
    }

    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        DashScopeAiEmbeddingOptions options = new DashScopeAiEmbeddingOptions();
        if (defaultOptions != null) {
            options = ModelOptionsUtils.merge(defaultOptions, options, DashScopeAiEmbeddingOptions.class);
        }
        if (request.getOptions() != null) {
            options = ModelOptionsUtils.merge(request.getOptions(), options, DashScopeAiEmbeddingOptions.class);
        }
        var embeddingParam = TextEmbeddingParam.builder().model(options.getModel())
                .texts(request.getInstructions()).build();
        var textEmbeddingResult = dashScopeAiApi.embedding(embeddingParam);
        List<Embedding> embeddings = textEmbeddingResult.getOutput().getEmbeddings().stream().map(e -> new Embedding(e.getEmbedding(), e.getTextIndex()))
                .toList();
        var metadata = new EmbeddingResponseMetadata();
        metadata.setUsage(new Usage() {
            @Override
            public Long getPromptTokens() {
                return textEmbeddingResult.getUsage().getTotalTokens().longValue();
            }

            @Override
            public Long getGenerationTokens() {
                return 0L;
            }
        });
        return new EmbeddingResponse(embeddings, metadata);
    }

    @Override
    public List<Double> embed(Document document) {
        return this.embed(document.getFormattedContent(this.metadataMode));
    }

}
