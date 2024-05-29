package io.qifan.server.ai.message.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.ai.session.entity.AiSession;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.*;

import java.util.List;
import java.util.Map;


@Entity
@Table(name = "ai_message")
@GenEntity
public interface AiMessage extends BaseEntity {

    /**
     * 消息类型(用户/助手/系统)
     */
    @GenField(value = "消息类型", type = ItemType.SELECTABLE, dictEnName = DictConstants.AI_MESSAGE_TYPE, order = 0)
    DictConstants.AiMessageType type();

    /**
     * 消息内容
     */
    @Serialized
    @GenField(value = "消息内容", type = ItemType.INPUT_TEXT_AREA, order = 1)
    List<Map<String, Object>> content();

    /**
     * 会话id
     */
    @GenField(value = "会话", type = ItemType.ASSOCIATION_SELECT, order = 2)
    @IdView
    String aiSessionId();

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    AiSession aiSession();

}
