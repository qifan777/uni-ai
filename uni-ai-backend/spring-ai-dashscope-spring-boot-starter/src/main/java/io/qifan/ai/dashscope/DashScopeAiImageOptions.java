package io.qifan.ai.dashscope;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.ai.image.ImageOptions;

@Data
public class DashScopeAiImageOptions implements ImageOptions {
    @JsonProperty("model")
    private String model;
    @JsonProperty("n")
    private Integer n;
    @JsonProperty("height")
    private Integer height;
    @JsonProperty("width")
    private Integer width;

    @Override
    public String getResponseFormat() {
        return "";
    }
}
