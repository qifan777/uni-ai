package io.qifan.ai.qianfan;

import lombok.Data;
import org.springframework.ai.chat.prompt.ChatOptions;

@Data
public class QianFanAiChatOptions implements ChatOptions {
    private String model;
    private Float temperature;
    private Integer maxTokens;
    private Integer topK;
    private Float topP;
}
