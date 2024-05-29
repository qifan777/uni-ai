package io.qifan.server.role.repository;

import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.role.entity.Role;
import io.qifan.server.role.entity.RoleFetcher;
import io.qifan.server.role.entity.RoleTable;
import io.qifan.server.role.entity.dto.RoleSpec;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleRepository extends JRepository<Role, String> {

  RoleTable roleTable = RoleTable.$;
  RoleFetcher COMPLEX_FETCHER = RoleFetcher.$.allScalarFields()
      .creator(UserFetcher.$.phone().nickname())
      .editor(UserFetcher.$.phone().nickname());
  RoleFetcher ROLE_MENU_FETCHER = RoleFetcher.$.allScalarFields().menusView(true);

  default Page<Role> findPage(QueryRequest<RoleSpec> queryRequest, Fetcher<Role> fetcher) {
    RoleSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(roleTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(roleTable, pageable.getSort()))
        .select(roleTable.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}