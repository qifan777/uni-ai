package io.qifan.server.ai.message.entity.dto;

import io.qifan.server.ai.message.entity.model.ChatParams;
import lombok.Data;

import java.util.Map;

@Data
public class ChatMessageRequest {
    AiMessageCreateInput message;
    Map<String, Object> chatOptions;
    Map<String, Object> imageOptions;
    ChatParams chatParams;
}
