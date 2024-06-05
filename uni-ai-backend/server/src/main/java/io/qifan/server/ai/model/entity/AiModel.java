package io.qifan.server.ai.model.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.ai.tag.model.entity.AiModelTagRel;
import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.ManyToManyView;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.Serialized;

import java.util.List;
import java.util.Map;

/**
 * Entity for table "AI模型"
 */
@GenEntity
@Entity
public interface AiModel extends BaseEntity {
    /**
     * 模型
     */
    @GenField(value = "模型", order = 0)
    String name();

    /**
     * 厂家
     */

    @GenField(value = "厂家", type = ItemType.SELECTABLE, dictEnName = DictConstants.AI_FACTORY_TYPE, order = 1)
    DictConstants.AiFactoryType factory();

    @OneToMany(mappedBy = "aiModel")
    List<AiModelTagRel> tags();

    @ManyToManyView(prop = "tags")
    List<AiTag> tagsView();

    @Null
    @Serialized
    Map<String, Object> options();
}

