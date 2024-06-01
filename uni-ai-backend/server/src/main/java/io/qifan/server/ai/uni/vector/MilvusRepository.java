package io.qifan.server.ai.uni.vector;

import io.milvus.client.MilvusServiceClient;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.collection.entity.AiCollection;
import io.qifan.server.ai.collection.entity.AiCollectionFetcher;
import io.qifan.server.ai.collection.repository.AiCollectionRepository;
import io.qifan.server.ai.model.entity.AiModelFetcher;
import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.ai.tag.root.entity.AiTagFetcher;
import io.qifan.server.ai.uni.embedding.UniAiEmbeddingService;
import io.qifan.server.dict.model.DictConstants;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.MilvusVectorStore;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class MilvusRepository {
    private final Map<String, UniAiEmbeddingService> embeddingServiceMap;
    private final MilvusServiceClient milvusServiceClient;
    private final AiCollectionRepository aiCollectionRepository;


    @SneakyThrows
    public void embedding(List<Document> documents, String collectionId) {
        MilvusVectorStore milvusVectorStore = getMilvusVectorStore(collectionId);
        milvusVectorStore.afterPropertiesSet();
        milvusVectorStore.add(documents);
    }

    @SneakyThrows
    public List<Document> similaritySearch(String query, String collectionId) {
        MilvusVectorStore milvusVectorStore = getMilvusVectorStore(collectionId);
        milvusVectorStore.afterPropertiesSet();
        return milvusVectorStore.similaritySearch(query);
    }

    public MilvusVectorStore getMilvusVectorStore(String collectionId) {
        AiCollection aiCollection = aiCollectionRepository.findById(collectionId, AiCollectionFetcher.$.allScalarFields()
                        .embeddingModel(AiModelFetcher.$
                                .allScalarFields()
                                .tagsView(AiTagFetcher.$.allScalarFields())))
                .orElseThrow(() -> new BusinessException("知识库不存在"));
        AiTag aiTag = aiCollection.embeddingModel().tagsView().stream().filter(tag -> tag.name().equals(DictConstants.AiModelTag.EMBEDDINGS))
                .findFirst()
                .orElseThrow(() -> new BusinessException("模型未配置Embeddings标签"));
        UniAiEmbeddingService uniAiEmbeddingService = embeddingServiceMap.get(StringUtils.uncapitalize(aiTag.springAiModel()));
        if (uniAiEmbeddingService == null) {
            throw new BusinessException("暂不支持该模型");
        }
        Map<String, Object> options = aiCollection.embeddingModel().options();
        EmbeddingModel embeddingModel = uniAiEmbeddingService.getEmbeddingModel(options);

        return new MilvusVectorStore(milvusServiceClient, embeddingModel, MilvusVectorStore.MilvusVectorStoreConfig.builder()
                .withCollectionName(aiCollection.collectionName())
                .withEmbeddingDimension(options.containsKey("dimension") ? (int) options.get("dimension") : MilvusVectorStore.OPENAI_EMBEDDING_DIMENSION_SIZE)
                .build(), true);
    }
}
