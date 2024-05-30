package io.qifan.ai.qianfan;

import io.qifan.ai.qianfan.api.QianFanApi;
import org.springframework.ai.document.Document;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.embedding.*;

import java.math.BigDecimal;
import java.util.List;

public class QifanAiEmbeddingModel implements EmbeddingModel {
    private final QianFanApi qianFanApi;
    private final MetadataMode metadataMode;
    private QifanAiEmbeddingOptions embeddingOptions;

    public QifanAiEmbeddingModel(QianFanApi qianFanApi, MetadataMode metadataMode, QifanAiEmbeddingOptions embeddingOptions) {
        this.qianFanApi = qianFanApi;
        this.metadataMode = metadataMode;
        this.embeddingOptions = embeddingOptions;
    }

    @Override
    public EmbeddingResponse call(EmbeddingRequest request) {
        return toResponse(qianFanApi.embedding(toRequest(request)));
    }

    @Override
    public List<Double> embed(Document document) {
        return this.embed(document.getFormattedContent(this.metadataMode));
    }

    public com.baidubce.qianfan.model.embedding.EmbeddingRequest toRequest(EmbeddingRequest request) {
        EmbeddingOptions embeddingOptions = request.getOptions();
        if (embeddingOptions instanceof QifanAiEmbeddingOptions) {
            this.embeddingOptions = (QifanAiEmbeddingOptions) embeddingOptions;
        }
        return new com.baidubce.qianfan.model.embedding.EmbeddingRequest().setModel(this.embeddingOptions.getModel()).setInput(request.getInstructions());
    }

    public EmbeddingResponse toResponse(com.baidubce.qianfan.model.embedding.EmbeddingResponse response) {
        EmbeddingResponseMetadata metadata = new EmbeddingResponseMetadata();
        metadata.put("totalTokens", response.getUsage().getTotalTokens());
        return new EmbeddingResponse(response.getData()
                .stream()
                .map(embeddingData -> new Embedding(embeddingData
                        .getEmbedding()
                        .stream()
                        .map(BigDecimal::doubleValue)
                        .toList(), embeddingData.getIndex()))
                .toList(), metadata);
    }
}
