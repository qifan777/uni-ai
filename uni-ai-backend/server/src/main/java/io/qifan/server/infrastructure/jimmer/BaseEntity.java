package io.qifan.server.infrastructure.jimmer;

import io.qifan.server.user.root.entity.User;
import org.babyfish.jimmer.sql.DissociateAction;
import org.babyfish.jimmer.sql.GeneratedValue;
import org.babyfish.jimmer.sql.Id;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.MappedSuperclass;
import org.babyfish.jimmer.sql.OnDissociate;

@MappedSuperclass
public interface BaseEntity extends BaseDateTime {

  @Id
  @GeneratedValue(generatorType = UUIDIdGenerator.class)
  String id();

  @ManyToOne
  @OnDissociate(DissociateAction.SET_NULL)
  User editor();

  @ManyToOne
  @OnDissociate(DissociateAction.SET_NULL)
  User creator();
}