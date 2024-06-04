package io.qifan.ai.kimi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.ai.chat.prompt.ChatOptions;

@Data
@Accessors(chain = true)
public class KimiAiChatOptions implements ChatOptions {
    @JsonProperty("model")
    private String model;
    @JsonProperty("temperature")
    private Float temperature;
    @JsonProperty("maxTokens")
    private Integer maxTokens;
    @JsonProperty("topK")
    private Integer topK;
    @JsonProperty("topP")
    private Float topP;
}
