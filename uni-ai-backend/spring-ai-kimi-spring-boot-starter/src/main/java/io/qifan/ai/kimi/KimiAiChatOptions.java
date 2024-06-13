package io.qifan.ai.kimi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.qifan.ai.kimi.api.KimiAiApi;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallingOptions;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class KimiAiChatOptions implements ChatOptions, FunctionCallingOptions {
    @JsonProperty("model")
    private String model;
    @JsonProperty("frequency_penalty")
    private Float frequencyPenalty;
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    @JsonProperty("n")
    private Integer n;
    @JsonProperty("presence_penalty")
    private Float presencePenalty;
    @NestedConfigurationProperty
    @JsonProperty("stop")
    private List<String> stop;
    @JsonProperty("temperature")
    private Float temperature;
    @JsonProperty("top_p")
    private Float topP;
    @NestedConfigurationProperty
    @JsonProperty("tools")
    private List<KimiAiApi.FunctionTool> tools;
    @JsonProperty("tool_choice")
    private String toolChoice;
    @JsonProperty("user")
    private String user;
    @NestedConfigurationProperty
    @JsonIgnore
    private List<FunctionCallback> functionCallbacks = new ArrayList<>();
    @NestedConfigurationProperty
    @JsonIgnore
    private Set<String> functions = new HashSet<>();

    @Override
    public List<FunctionCallback> getFunctionCallbacks() {
        return this.functionCallbacks;
    }

    @Override
    public void setFunctionCallbacks(List<FunctionCallback> functionCallbacks) {
        this.functionCallbacks = functionCallbacks;
    }

    @Override
    public Set<String> getFunctions() {
        return this.functions;
    }

    @Override
    public void setFunctions(Set<String> functions) {
        this.functions = functions;
    }

    @JsonIgnore
    @Override
    public Integer getTopK() {
        throw new UnsupportedOperationException("Unimplemented method 'getTopK'");
    }

    @JsonIgnore
    public void setTopK(Integer topK) {
        throw new UnsupportedOperationException("Unimplemented method 'setTopK'");
    }

    public static KimiAiChatOptions fromOptions(KimiAiChatOptions fromOptions) {
        KimiAiChatOptions kimiAiChatOptions = new KimiAiChatOptions()
                .setN(fromOptions.getN())
                .setStop(fromOptions.getStop())
                .setModel(fromOptions.getModel())
                .setFrequencyPenalty(fromOptions.getFrequencyPenalty())
                .setTemperature(fromOptions.getTemperature())
                .setMaxTokens(fromOptions.getMaxTokens())
                .setPresencePenalty(fromOptions.getPresencePenalty())
                .setToolChoice(fromOptions.getToolChoice())
                .setTools(fromOptions.getTools())
                .setTopP(fromOptions.getTopP());
        kimiAiChatOptions.setFunctions(fromOptions.getFunctions());
        kimiAiChatOptions.setFunctionCallbacks(fromOptions.getFunctionCallbacks());
        return kimiAiChatOptions;
    }

}
