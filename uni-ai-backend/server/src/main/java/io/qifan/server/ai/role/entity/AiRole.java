package io.qifan.server.ai.role.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.ai.message.entity.dto.AiMessageView;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import jakarta.annotation.Nullable;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.Serialized;
import org.babyfish.jimmer.sql.Table;

import java.util.List;


@GenEntity
@Entity
@Table(name = "ai_role")
public interface AiRole extends BaseEntity {

    /**
     * 角色名称
     */
    @GenField(value = "角色名称", order = 0)
    @Key
    String name();

    /**
     * 描述
     */
    @GenField(value = "描述", order = 1)
    String description();

    /**
     * 图标
     */
    @Nullable
    @GenField(value = "图标", type = ItemType.PICTURE, order = 1)
    String icon();

    /**
     * 预置提示词
     */
    @GenField(value = "预置提示词", order = 2)
    @Serialized
    List<AiMessageView> prompts();

}
