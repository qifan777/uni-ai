package io.qifan.server.ai.message.entity.model;

import org.springframework.ai.chat.messages.AbstractMessage;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.MessageType;

import java.util.List;

public class ChatMessage extends AbstractMessage {
    protected ChatMessage(MessageType messageType, String content) {
        super(messageType, content);
    }

    public ChatMessage(MessageType messageType, String textContent, List<Media> media) {
        super(messageType, textContent, media);
    }
}
