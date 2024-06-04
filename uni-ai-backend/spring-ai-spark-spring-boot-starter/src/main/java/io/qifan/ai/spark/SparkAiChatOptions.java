package io.qifan.ai.spark;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.ai.chat.prompt.ChatOptions;

@Data
public class SparkAiChatOptions implements ChatOptions {
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("baseUrl")
    private String baseUrl;
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
