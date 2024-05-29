package io.qifan.server.user.root.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.infrastructure.jimmer.BaseDateTime;
import io.qifan.server.infrastructure.jimmer.UUIDIdGenerator;
import io.qifan.server.role.entity.Role;
import io.qifan.server.user.wechat.entity.UserWeChat;
import jakarta.validation.constraints.Null;
import org.babyfish.jimmer.sql.*;

import java.util.List;

@GenEntity
@Entity
public interface User extends BaseDateTime {

    @Id
    @GeneratedValue(generatorType = UUIDIdGenerator.class)
    String id();

    @GenField(value = "手机号", order = 0)
    @Key
    String phone();

    @GenField(value = "密码", order = 1)
    String password();

    @GenField(value = "昵称", order = 2)
    @Null
    String nickname();

    @GenField(value = "头像", order = 3, type = ItemType.PICTURE)
    @Null
    String avatar();

    @GenField(value = "性别", order = 4, type = ItemType.SELECTABLE, dictEnName = DictConstants.GENDER)
    @Null
    DictConstants.Gender gender();

    @Null
    @OneToOne(mappedBy = "user")
    UserWeChat wechat();


    @OneToMany(mappedBy = "user")
    List<UserRoleRel> roles();

    @ManyToManyView(
            prop = "roles",
            deeperProp = "role"
    )
    List<Role> rolesView();

    DictConstants.UserStatus status();
}
