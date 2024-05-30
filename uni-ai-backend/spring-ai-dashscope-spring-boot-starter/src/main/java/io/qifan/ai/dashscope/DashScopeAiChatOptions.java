package io.qifan.ai.dashscope;

import com.alibaba.dashscope.tools.ToolBase;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.ai.chat.prompt.ChatOptions;

import java.util.List;

@Data
@Accessors(chain = true)
public class DashScopeAiChatOptions implements ChatOptions {
    private String apiKey;
    private String model;
    private Float temperature;
    private Integer maxTokens;
    private Integer topK;
    private Float topP;
    private List<ToolBase> tools;
}
