package io.qifan.server.ai.uni.embedding;

import org.springframework.ai.embedding.EmbeddingModel;

import java.util.Map;

public interface UniAiEmbeddingService {
    EmbeddingModel getEmbeddingModel(Map<String, Object> options);
}
