package io.qifan.ai.qianfan.api;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.model.chat.ChatRequest;
import com.baidubce.qianfan.model.chat.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.concurrent.Executor;

@Slf4j
public class QianFanApi {
    private final String accessKey;
    private final String secretKey;
    private final Executor executor;

    public QianFanApi(String accessKey, String secretKey, Executor executor) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.executor = executor;
    }

    public ChatResponse chatCompletion(ChatRequest request) {
        Qianfan qianfan = new Qianfan(accessKey, secretKey);
        return qianfan.chatCompletion(request);
    }

    public Flux<ChatResponse> chatCompletionStream(ChatRequest request) {
        Qianfan qianfan = new Qianfan(accessKey, secretKey);
        return Flux.create(fluxSink -> {
            executor.execute(() -> qianfan.chatCompletionStream(request)
                    .forEachRemaining(chatResponse -> {
                        log.info("回复内容：{}", chatResponse.getResult());
                        fluxSink.next(chatResponse);
                        if (chatResponse.getEnd()) {
                            fluxSink.complete();
                        }
                    }));
        });
    }
}
