package io.qifan.server.infrastructure.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.server.menu.entity.Menu;
import io.qifan.server.menu.entity.MenuTable;
import io.qifan.server.role.entity.Role;
import io.qifan.server.role.entity.RoleTable;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.sql.JSqlClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RoleMenuStpImpl implements StpInterface {
    private final JSqlClient jSqlClient;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        MenuTable t = MenuTable.$;
        return jSqlClient
                .createQuery(t)
                .where(t.roles(roleMenuRelTableEx -> roleMenuRelTableEx
                        .role()
                        .users(userRoleRelTableEx -> userRoleRelTableEx
                                .userId().eq(StpUtil.getLoginIdAsString()))))
                .where(t.visible().eq(true))
                .select(t)
                .execute()
                .stream().map(Menu::path)
                .toList();

    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        var t = RoleTable.$;
        return jSqlClient.createQuery(t)
                .where(t.users(userRoleRelTableEx -> userRoleRelTableEx.userId().eq(StpUtil.getLoginIdAsString())))
                .select(t)
                .execute()
                .stream().map(Role::name)
                .toList();
    }
}
