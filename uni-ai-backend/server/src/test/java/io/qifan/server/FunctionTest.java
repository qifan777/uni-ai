package io.qifan.server;

import io.qifan.ai.kimi.KimiAiChatModel;
import io.qifan.ai.qianfan.QianFanAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

@SpringBootTest
public class FunctionTest {
    @Autowired
    QianFanAiChatModel qianFanAiChatModel;
    @Autowired
    KimiAiChatModel kimiAiChatModel;

    @Test
    public void functionCall() {
        Flux<String> springSpELFunction = ChatClient.create(kimiAiChatModel)
                .prompt()
                .user("用springSpEL算一下1+2+3+4-2/1*2-6/6-1+7/6+2+3+4-2/1*2-6/6-1+7等于多少？")
                .functions("springSpELFunction")
                .stream()
                .content()
                .log();
        springSpELFunction.blockLast();
    }
}
