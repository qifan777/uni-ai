package io.qifan.server.ai.message.service.image;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.message.entity.dto.ChatMessageRequest;
import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.ai.session.entity.AiSession;
import io.qifan.server.ai.session.repository.AiSessionRepository;
import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.ai.uni.image.UniAiImageService;
import lombok.AllArgsConstructor;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
@AllArgsConstructor
public class AiMessageImageService {
    private final AiSessionRepository aiSessionRepository;
    Map<String, UniAiImageService> uniAiImageServiceMap;

    public ImageResponse generate(ChatMessageRequest input) {
        AiSession aiSession = aiSessionRepository.findById(input.getMessage().getAiSessionId(), AiSessionRepository.COMPLEX_FETCHER_FOR_FRONT)
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "会话不存在"));
        AiModel model = aiSession.aiModel();
        if (model == null) {
            throw new BusinessException("请配置模型");
        }
        if (model.tagsView() == null) {
            throw new BusinessException("请配置模型标签");
        }
        AiTag aiTag = model.tagsView().stream().filter(tag -> tag.name().equals(input.getChatParams().getTag()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("模型不支持该场景"));
        UniAiImageService aiImageService = uniAiImageServiceMap.get(StringUtils.uncapitalize(aiTag.service()));
        if (aiImageService == null) {
            throw new BusinessException("后端未配置模型服务");
        }
        Map<String, Object> options = model.aiFactory().options();
        options.putAll(model.options());
        if (input.getImageOptions() != null) {
            options.putAll(input.getImageOptions());
        }
        return aiImageService.getImageMode(options).call(new ImagePrompt((String) input.getMessage().getContent().get(0).get("text")));
    }
}
