package io.qifan.server.ai.message.entity.model;

import io.qifan.server.dict.model.DictConstants;
import lombok.Data;
import org.babyfish.jimmer.sql.EnableDtoGeneration;

import java.util.Map;

@Data
@EnableDtoGeneration
public class ChatParams {
    DictConstants.AiModelTag tag;
    String collectionId;
    String aiModelId;
    String aiRoleId;
    Map<String, Object> options;
}