package io.qifan.server;

import io.qifan.ai.kimi.KimiAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
public class ChatTest {
    @Autowired
    KimiAiChatModel kimiAiChatModel;

    @Test
    public void chat() {
        Flux<String> content = ChatClient.create(kimiAiChatModel)
                .prompt()
                .user("你好")
                .stream()
                .content()
                .log();
        content.blockLast();
    }
}
