package io.qifan.server.ai.message.entity.model;

import io.qifan.server.dict.model.DictConstants;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ChatParams {
    DictConstants.AiModelTag tag;
    String aiCollectionId;
    String aiModelId;
    String aiRoleId;
    List<String> pluginNames;
    Map<String, Object> options;
}