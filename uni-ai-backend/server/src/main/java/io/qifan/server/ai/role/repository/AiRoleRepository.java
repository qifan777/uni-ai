package io.qifan.server.ai.role.repository;

import io.qifan.server.ai.role.entity.AiRole;
import io.qifan.server.ai.role.entity.AiRoleFetcher;
import io.qifan.server.ai.role.entity.AiRoleTable;
import io.qifan.server.ai.role.entity.dto.AiRoleSpec;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiRoleRepository extends JRepository<AiRole, String> {
    AiRoleTable aiRoleTable = AiRoleTable.$;
    AiRoleFetcher COMPLEX_FETCHER_FOR_ADMIN = AiRoleFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    AiRoleFetcher COMPLEX_FETCHER_FOR_FRONT = AiRoleFetcher.$.allScalarFields()
            .creator(true);

    default Page<AiRole> findPage(QueryRequest<AiRoleSpec> queryRequest,
                                  Fetcher<AiRole> fetcher) {
        AiRoleSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(aiRoleTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(aiRoleTable, pageable.getSort()))
                .select(aiRoleTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}