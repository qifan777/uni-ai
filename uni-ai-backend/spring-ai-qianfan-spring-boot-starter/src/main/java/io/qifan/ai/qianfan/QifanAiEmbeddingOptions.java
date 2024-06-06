package io.qifan.ai.qianfan;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.ai.embedding.EmbeddingOptions;

@Data
@Accessors(chain = true)
public class QifanAiEmbeddingOptions implements EmbeddingOptions {
    @JsonProperty("model")
    String model;
}
