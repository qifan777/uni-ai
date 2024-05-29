package io.qifan.server.ai.tag.root.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Table;


/**
 * <p>
 * spring ai model标签
 * </p>
 */
@GenEntity
@Entity
@Table(name = "ai_tag")
public interface AiTag extends BaseEntity {

    /**
     * 标签
     */
    @GenField(value = "标签", type = ItemType.SELECTABLE, dictEnName = DictConstants.AI_MODEL_TAG, order = 0)
    DictConstants.AiModelTag name();

    /**
     * 厂家
     */
    @GenField(value = "厂家", type = ItemType.SELECTABLE, dictEnName = DictConstants.AI_FACTORY_TYPE, order = 1)
    DictConstants.AiFactoryType factory();

    /**
     * SpringAIModel
     */
    @GenField(value = "SpringAIModel")
    String springAiModel();

}
