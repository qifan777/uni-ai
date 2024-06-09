package io.qifan.ai.dashscope;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.util.List;

@Data
@Accessors(chain = true)
public class DashScopeAiChatOptions implements ChatOptions {
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
    @JsonProperty("functions")
    private List<String> functions;
}
