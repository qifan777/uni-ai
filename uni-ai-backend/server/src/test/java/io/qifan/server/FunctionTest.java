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
                .user("C:\\Users\\Administrator\\Desktop\\资料\\19信管1班1916411024林家成.pdf，这份简历的亮点是什么？")
                .functions("documentAnalyzerFunction")
                .stream()
                .content()
                .log();
        springSpELFunction.blockLast();
    }
}
