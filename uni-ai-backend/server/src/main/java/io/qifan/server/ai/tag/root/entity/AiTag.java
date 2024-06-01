package io.qifan.server.ai.tag.root.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.ai.factory.entity.AiFactory;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;
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

    @GenField(value = "厂家", type = ItemType.ASSOCIATION_SELECT, order = 1)
    @IdView
    String aiFactoryId();

    /**
     * 厂家
     */
    @ManyToOne
    AiFactory aiFactory();

    /**
     * service
     */
    @GenField(value = "service", order = 2)
    String service();

}
