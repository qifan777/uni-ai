package io.qifan.ai.dashscope;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.ai.embedding.EmbeddingOptions;

import static com.alibaba.dashscope.tokenizers.Tokenization.Models.TEXT_EMBEDDING_V1;

@Data
@Accessors(chain = true)
public class DashScopeAiEmbeddingOptions implements EmbeddingOptions {
    @JsonProperty("model")
    private String model=TEXT_EMBEDDING_V1;
}
