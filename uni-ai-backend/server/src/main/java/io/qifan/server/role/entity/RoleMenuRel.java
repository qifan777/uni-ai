package io.qifan.server.role.entity;

import io.qifan.server.infrastructure.jimmer.BaseEntity;
import io.qifan.server.menu.entity.Menu;
import org.babyfish.jimmer.sql.DissociateAction;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Key;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.OnDissociate;

@Entity
public interface RoleMenuRel extends BaseEntity {

  @OnDissociate(DissociateAction.DELETE)
  @ManyToOne
  @Key
  Role role();

  @OnDissociate(DissociateAction.DELETE)
  @ManyToOne
  @Key
  Menu menu();
}
