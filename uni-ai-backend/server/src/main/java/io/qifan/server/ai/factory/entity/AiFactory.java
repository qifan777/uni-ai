package io.qifan.server.ai.factory.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Serialized;

import java.util.Map;


/**
 * <p>
 * AI厂家
 * </p>
 */
@GenEntity
@Entity
public interface AiFactory extends BaseEntity {

    /**
     * 厂家名称
     */
    @GenField(value = "厂家名称", type = ItemType.SELECTABLE, dictEnName = DictConstants.AI_FACTORY_TYPE, order = 0)
    DictConstants.AiFactoryType name();

    /**
     * 厂家描述
     */
    @Null
    @GenField(value = "厂家描述", order = 1)
    String description();

    /**
     * options
     */
    @Null
    @Serialized
    Map<String, Object> options();
}
