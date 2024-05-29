package io.qifan.ai.spark;

import lombok.Data;
import org.springframework.ai.chat.prompt.ChatOptions;

@Data
public class SparkAiChatOptions implements ChatOptions {
    private String domain;
    private String baseUrl;
    private Float temperature = 0.5f;
    private Integer maxTokens = 4096;
    private Integer topK;
    private Float topP;
}
