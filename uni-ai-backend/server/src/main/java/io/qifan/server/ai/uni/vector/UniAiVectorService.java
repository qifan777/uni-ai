package io.qifan.server.ai.uni.vector;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;

import java.util.List;

public interface UniAiVectorService {
    void embedding(List<Document> documents, String collectionId);

    List<Document> similaritySearch(String query, String collectionId);

    VectorStore getVectorStore(String collectionId);
    void deleteCollection(String collectionId);
}
