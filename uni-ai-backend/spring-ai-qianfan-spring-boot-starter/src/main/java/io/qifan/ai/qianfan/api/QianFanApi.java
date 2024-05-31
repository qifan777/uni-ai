package io.qifan.ai.qianfan.api;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.model.chat.ChatRequest;
import com.baidubce.qianfan.model.chat.ChatResponse;
import com.baidubce.qianfan.model.embedding.EmbeddingRequest;
import com.baidubce.qianfan.model.embedding.EmbeddingResponse;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class QianFanApi {
    private final String accessKey;
    private final String secretKey;

    public QianFanApi(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public ChatResponse chatCompletion(ChatRequest request) {
        Qianfan qianfan = new Qianfan(accessKey, secretKey);
        return qianfan.chatCompletion(request);
    }

    public Flux<ChatResponse> chatCompletionStream(ChatRequest request) {
        Qianfan qianfan = new Qianfan(accessKey, secretKey);
        return Flux.create(fluxSink -> {
            qianfan.chatCompletionStream(request)
                    .forEachRemaining(chatResponse -> {
                        log.info("回复内容：{}", chatResponse.getResult());
                        fluxSink.next(chatResponse);
                        if (chatResponse.getEnd()) {
                            fluxSink.complete();
                        }
                    });
        });
    }

    public EmbeddingResponse embedding(EmbeddingRequest request) {
        Qianfan qianfan = new Qianfan(accessKey, secretKey);
        return qianfan.embedding(request);
    }
}
