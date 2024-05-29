package io.qifan.server.user.root.repository;

import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.role.entity.RoleFetcher;
import io.qifan.server.user.root.entity.User;

import io.qifan.server.user.root.entity.UserFetcher;
import io.qifan.server.user.root.entity.UserTable;
import io.qifan.server.user.root.entity.dto.UserSpec;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JRepository<User, String> {

    UserTable userTable = UserTable.$;
    UserFetcher COMPLEX_FETCHER_FOR_ADMIN = UserFetcher.$.allScalarFields();
    UserFetcher COMPLEX_FETCHER_FOR_FRONT= UserFetcher.$.allScalarFields();
    UserFetcher USER_ROLE_FETCHER = UserFetcher.$.allScalarFields().rolesView(RoleFetcher.$.name());

    default Page<User> findPage(QueryRequest<UserSpec> queryRequest, Fetcher<User> fetcher) {
        UserSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(userTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(userTable, pageable.getSort()))
                .select(userTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}