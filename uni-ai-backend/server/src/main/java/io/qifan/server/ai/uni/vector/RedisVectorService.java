package io.qifan.server.ai.uni.vector;

import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.collection.entity.AiCollection;
import io.qifan.server.ai.collection.entity.AiCollectionFetcher;
import io.qifan.server.ai.collection.repository.AiCollectionRepository;
import io.qifan.server.ai.factory.entity.AiFactoryFetcher;
import io.qifan.server.ai.model.entity.AiModelFetcher;
import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.ai.tag.root.entity.AiTagFetcher;
import io.qifan.server.ai.uni.embedding.UniAiEmbeddingService;
import io.qifan.server.dict.model.DictConstants;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.RedisVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisConnectionDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class RedisVectorService implements UniAiVectorService {
    private final Map<String, UniAiEmbeddingService> embeddingServiceMap;
    private final AiCollectionRepository aiCollectionRepository;
    private final RedisConnectionDetails redisConnectionDetails;


    @SneakyThrows
    public void embedding(List<Document> documents, String collectionId) {
        VectorStore vectorStore = getVectorStore(collectionId);
        if (vectorStore instanceof InitializingBean store) {
            store.afterPropertiesSet();
        }
        vectorStore.add(documents);
    }

    @SneakyThrows
    public List<Document> similaritySearch(String query, String collectionId) {
        VectorStore vectorStore = getVectorStore(collectionId);
        if (vectorStore instanceof InitializingBean store) {
            store.afterPropertiesSet();
        }
        return vectorStore.similaritySearch(query);
    }

    public VectorStore getVectorStore(String collectionId) {
        AiCollection aiCollection = aiCollectionRepository.findById(collectionId, AiCollectionFetcher.$.allScalarFields()
                        .embeddingModel(AiModelFetcher.$
                                .allScalarFields()
                                .aiFactory(AiFactoryFetcher.$.allScalarFields())
                                .tagsView(AiTagFetcher.$.allScalarFields())))
                .orElseThrow(() -> new BusinessException("知识库不存在"));
        AiTag aiTag = aiCollection.embeddingModel().tagsView().stream().filter(tag -> tag.name().equals(DictConstants.AiModelTag.EMBEDDINGS))
                .findFirst()
                .orElseThrow(() -> new BusinessException("模型未配置Embeddings标签"));
        UniAiEmbeddingService uniAiEmbeddingService = embeddingServiceMap.get(StringUtils.uncapitalize(aiTag.service()));
        if (uniAiEmbeddingService == null) {
            throw new BusinessException("暂不支持该模型");
        }
        Map<String, Object> options = aiCollection.embeddingModel().aiFactory().options();
        options.putAll(aiCollection.embeddingModel().options());
        EmbeddingModel embeddingModel = uniAiEmbeddingService.getEmbeddingModel(options);
        String username = StringUtils.hasText(redisConnectionDetails.getUsername()) ? redisConnectionDetails.getUsername() : "default";
        RedisVectorStore.RedisVectorStoreConfig config = RedisVectorStore.RedisVectorStoreConfig.builder()
                .withURI("redis://+" + username + ":" +
                        redisConnectionDetails.getPassword() + "@" +
                        redisConnectionDetails.getStandalone().getHost() + ":" +
                        redisConnectionDetails.getStandalone().getPort())
                .withIndexName(aiCollection.collectionName())
                .build();
        return new RedisVectorStore(config, embeddingModel, true);
    }
}
