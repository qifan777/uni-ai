package io.qifan.ai.dashscope;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class DashScopeAiChatOptions implements ChatOptions, FunctionCallingOptions {
    @JsonProperty("model")
    private String model;
    @JsonProperty("temperature")
    private Float temperature;
    @JsonProperty("maxTokens")
    private Integer maxTokens;
    @JsonProperty("topP")
    private Float topP;
    @JsonProperty("incrementalOutput")
    private Boolean incrementalOutput;
    @NestedConfigurationProperty
    @JsonIgnore
    private List<FunctionCallback> functionCallbacks = new ArrayList<>();
    @NestedConfigurationProperty
    @JsonIgnore
    private Set<String> functions = new HashSet<>();

    public static DashScopeAiChatOptions fromOptions(DashScopeAiChatOptions fromOptions) {
        DashScopeAiChatOptions chatOptions = new DashScopeAiChatOptions()
                .setModel(fromOptions.getModel())
                .setTemperature(fromOptions.getTemperature())
                .setMaxTokens(fromOptions.getMaxTokens())
                .setTopP(fromOptions.getTopP());
        chatOptions.setFunctions(fromOptions.getFunctions());
        chatOptions.setFunctionCallbacks(fromOptions.getFunctionCallbacks());
        return chatOptions;
    }

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

    @Override
    public ChatOptions copy() {
        return fromOptions(this);
    }

    @JsonIgnore
    public void setTopK(Integer topK) {
        throw new UnsupportedOperationException("Unimplemented method 'setTopK'");
    }
}
