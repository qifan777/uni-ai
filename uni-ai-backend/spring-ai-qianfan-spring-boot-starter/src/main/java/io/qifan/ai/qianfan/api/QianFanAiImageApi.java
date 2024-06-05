package io.qifan.ai.qianfan.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class QianFanAiImageApi {
    private final String accessKey;
    private final String secretKey;
    public final Map<String, String> modelMap = Map.of("stable-diffusion-xl-base-1.0", "sd_xl");

    @Data
    @Accessors(chain = true)
    public static class QianFanAiImageRequest {
        @JsonProperty("prompt")
        private String prompt;
        @JsonProperty("negative_prompt")
        private String negativePrompt;
        @JsonProperty("size")
        private String size;
        @JsonProperty("n")
        private Integer n;
        @JsonProperty("steps")
        private Integer steps;
        @JsonProperty("user")
        private Integer user;
        @JsonIgnore
        private String model;
        @JsonProperty("style")
        private String style;
    }

    @Data
    @Accessors(chain = true)
    public static class QianFanAiImageResponse {
        @JsonProperty("id")
        private String id;
        @JsonProperty("object")
        private String object;
        @JsonProperty("created")
        private Integer created;
        @JsonProperty("data")
        private List<QianFanAiImageData> data;
        @JsonProperty("usage")
        private QianFanAiImageUsage usage;
    }

    @Data
    @Accessors(chain = true)
    public static class QianFanAiImageData {
        @JsonProperty("object")
        private String object;
        @JsonProperty("b4_image")
        private String b4Image;
        @JsonProperty("index")
        private String index;
    }

    @Accessors(chain = true)
    @Data
    public static class QianFanAiImageUsage {
        @JsonProperty("prompt_tokens")
        private Integer promptTokens;
        @JsonProperty("total_tokens")
        private Integer totalTokens;
    }
}
