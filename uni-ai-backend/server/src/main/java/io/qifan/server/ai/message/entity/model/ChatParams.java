package io.qifan.server.ai.message.entity.model;

import io.qifan.server.dict.model.DictConstants;
import lombok.Data;

@Data
public class ChatParams {
    DictConstants.AiModelTag tag;
    Boolean knowledge;
    String embeddingModelId;
}