package io.qifan.server.menu.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.dict.model.DictConstants.MenuType;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import io.qifan.server.role.entity.RoleMenuRel;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.OneToMany;

import java.util.List;

@GenEntity
@Entity
public interface Menu extends BaseEntity {

  @GenField(value = "菜单名称", order = 0)
  String name();

  @GenField(value = "父菜单Id", order = 1)
  @Null
  String parentId();

  @GenField(value = "路由路径", order = 2)
  String path();

  @GenField(value = "排序号", order = 3)
  Integer orderNum();

  @GenField(value = "菜单类型", type = ItemType.SELECTABLE, dictEnName = DictConstants.MENU_TYPE, order = 4)
  MenuType menuType();

  @GenField(value = "图标", type = ItemType.PICTURE, order = 5)
  @Null
  String icon();

  @OneToMany(mappedBy = "menu")
  List<RoleMenuRel> roles();


  @GenField(value = "是否可见")
  boolean visible();
}
