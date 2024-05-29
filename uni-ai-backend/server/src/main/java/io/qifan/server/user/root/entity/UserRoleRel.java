package io.qifan.server.user.root.entity;

import io.qifan.server.infrastructure.jimmer.BaseEntity;
import io.qifan.server.role.entity.Role;
import org.babyfish.jimmer.sql.*;

@Entity
public interface UserRoleRel extends BaseEntity {

    @ManyToOne
    @Key
    @OnDissociate(DissociateAction.DELETE)
    User user();

    @OnDissociate(DissociateAction.DELETE)
    @ManyToOne
    @Key
    Role role();
}
