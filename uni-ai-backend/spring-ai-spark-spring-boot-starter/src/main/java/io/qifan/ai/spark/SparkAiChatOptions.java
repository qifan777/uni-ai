package io.qifan.ai.spark;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.util.List;

@Accessors(chain = true)
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

    public static SparkAiChatOptions fromOptions(SparkAiChatOptions fromOptions) {
        return new SparkAiChatOptions()
                .setModel(fromOptions.getModel())
                .setBaseUrl(fromOptions.getBaseUrl())
                .setDomain(fromOptions.getDomain())
                .setTopK(fromOptions.getTopK())
                .setTopP(fromOptions.getTopP())
                .setTemperature(fromOptions.getTemperature())
                .setMaxTokens(fromOptions.getMaxTokens());
    }

    @Override
    public Float getFrequencyPenalty() {
        return 0f;
    }

    @Override
    public Float getPresencePenalty() {
        return 0f;
    }

    @Override
    public List<String> getStopSequences() {
        return List.of();
    }

    @Override
    public ChatOptions copy() {
        return fromOptions(this);
    }
}
