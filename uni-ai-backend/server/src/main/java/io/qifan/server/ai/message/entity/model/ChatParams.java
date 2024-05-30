package io.qifan.server.ai.message.entity.model;

import io.qifan.server.dict.model.DictConstants;
import lombok.Data;
import org.babyfish.jimmer.sql.EnableDtoGeneration;

@Data
@EnableDtoGeneration
public class ChatParams {
    DictConstants.AiModelTag tag;
    Boolean knowledge;
    String collectionId;
}