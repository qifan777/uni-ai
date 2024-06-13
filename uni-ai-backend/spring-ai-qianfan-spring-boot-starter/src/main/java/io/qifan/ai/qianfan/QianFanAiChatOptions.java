package io.qifan.ai.qianfan;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.model.function.FunctionCallback;
import org.springframework.ai.model.function.FunctionCallingOptions;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class QianFanAiChatOptions implements ChatOptions, FunctionCallingOptions {
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
}
