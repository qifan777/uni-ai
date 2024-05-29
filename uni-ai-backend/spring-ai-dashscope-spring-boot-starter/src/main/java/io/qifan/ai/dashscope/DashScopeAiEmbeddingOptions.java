package io.qifan.ai.dashscope;

import lombok.Data;

import static com.alibaba.dashscope.tokenizers.Tokenization.Models.TEXT_EMBEDDING_V1;

@Data
public class DashScopeAiEmbeddingOptions {
    private String model=TEXT_EMBEDDING_V1;
}
