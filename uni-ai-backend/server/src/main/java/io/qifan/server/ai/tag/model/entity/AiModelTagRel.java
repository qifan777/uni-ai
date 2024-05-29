package io.qifan.server.ai.tag.model.entity;

import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.*;


/**
 * <p>
 * ai_model_tag_rel
 * </p>
 */
@Entity
@Table(name = "ai_model_tag_rel")
public interface AiModelTagRel extends BaseEntity {

    /**
     * 标签
     */
    @IdView
    String aiTagId();

    @Key
    @ManyToOne
    AiTag aiTag();

    /**
     * 模型
     */
    @IdView
    String aiModelId();

    @Key
    @ManyToOne
    AiModel aiModel();

}
