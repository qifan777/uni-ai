package io.qifan.ai.qianfan;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.ai.embedding.EmbeddingOptions;

@Data
@Accessors(chain = true)
public class QifanAiEmbeddingOptions implements EmbeddingOptions {
    String model;
}
