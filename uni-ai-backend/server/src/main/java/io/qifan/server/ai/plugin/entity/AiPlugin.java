package io.qifan.server.ai.plugin.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.server.ai.plugin.entity.model.Parameter;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Serialized;


/**
 * <p>
 * AI插件
 * </p>
 */
@GenEntity
@Entity
public interface AiPlugin extends BaseEntity {

    /**
     * 函数名称
     */
    @GenField(value = "函数名称", order = 0)
    String name();

    /**
     * 函数描述
     */
    @GenField(value = "函数描述", order = 1)
    String description();

}
