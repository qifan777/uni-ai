package io.qifan.server.ai.session.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.ai.message.entity.AiMessage;
import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.ai.role.entity.AiRole;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@GenEntity
@Entity
@Table(name = "ai_session")
public interface AiSession extends BaseEntity {

    /**
     * 会话名称
     */
    @GenField(value = "会话名称", order = 0)
    String name();

    /**
     * 角色id
     */
    @IdView
    @GenField(value = "角色", type = ItemType.ASSOCIATION_SELECT, order = 1)
    @Nullable
    String aiRoleId();

    @ManyToOne
    @Nullable
    @OnDissociate(DissociateAction.DELETE)
    AiRole aiRole();

    @IdView
    String aiModelId();

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    AiModel aiModel();

    @OneToMany(mappedBy = "aiSession", orderedProps = @OrderedProp(value = "createdTime"))
    List<AiMessage> messages();
}
