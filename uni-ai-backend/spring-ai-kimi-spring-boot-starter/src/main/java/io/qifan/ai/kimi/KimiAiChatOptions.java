package io.qifan.ai.kimi;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.ai.chat.prompt.ChatOptions;

@Data
@Accessors(chain = true)
public class KimiAiChatOptions  implements ChatOptions {
    private String apiKey;
    private String model;
    private Float temperature;
    private Integer maxTokens;
    private Integer topK;
    private Float topP;
}
