package io.qifan.server.ai.message.service.image;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.ai.factory.entity.AiFactoryFetcher;
import io.qifan.server.ai.message.entity.dto.ChatMessageRequest;
import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.ai.model.entity.AiModelFetcher;
import io.qifan.server.ai.model.repository.AiModelRepository;
import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.ai.tag.root.entity.AiTagFetcher;
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
    private final AiModelRepository aiModelRepository;
    Map<String, UniAiImageService> uniAiImageServiceMap;

    public ImageResponse generate(ChatMessageRequest request) {
        AiModel aiModel = aiModelRepository.findById(request.getChatParams().getAiModelId(), AiModelFetcher.$.allScalarFields()
                        .aiFactory(AiFactoryFetcher.$.allScalarFields())
                        .tagsView(AiTagFetcher.$.allScalarFields()))
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "模型不存在"));

        if (aiModel == null) {
            throw new BusinessException("请配置模型");
        }
        if (aiModel.tagsView() == null) {
            throw new BusinessException("请配置模型标签");
        }
        AiTag aiTag = aiModel.tagsView().stream().filter(tag -> tag.name().equals(request.getChatParams().getTag()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("模型不支持该场景"));
        UniAiImageService aiImageService = uniAiImageServiceMap.get(StringUtils.uncapitalize(aiTag.service()));
        if (aiImageService == null) {
            throw new BusinessException("后端未配置模型服务");
        }
        Map<String, Object> options = aiModel.aiFactory().options();
        options.putAll(aiModel.options());
        if (request.getChatParams().getOptions() != null) {
            options.putAll(request.getChatParams().getOptions());
        }
        return aiImageService.getImageMode(options).call(new ImagePrompt((String) request.getMessage().getContent().get(0).get("text")));
    }
}
