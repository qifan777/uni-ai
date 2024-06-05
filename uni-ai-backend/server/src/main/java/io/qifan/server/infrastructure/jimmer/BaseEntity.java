package io.qifan.server.infrastructure.jimmer;

import io.qifan.server.user.root.entity.User;
import org.babyfish.jimmer.sql.*;

@MappedSuperclass
public interface BaseEntity extends BaseDateTime {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    String id();

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    User editor();

    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    User creator();
}