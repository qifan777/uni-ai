package io.qifan.server.ai.session.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.server.ai.message.entity.AiMessage;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.OneToMany;
import org.babyfish.jimmer.sql.OrderedProp;
import org.babyfish.jimmer.sql.Table;

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


    @OneToMany(mappedBy = "aiSession", orderedProps = @OrderedProp(value = "createdTime"))
    List<AiMessage> messages();
}
