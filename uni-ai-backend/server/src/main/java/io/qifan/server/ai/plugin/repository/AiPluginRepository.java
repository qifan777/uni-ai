package io.qifan.server.ai.plugin.repository;

import io.qifan.server.ai.plugin.entity.AiPlugin;
import io.qifan.server.ai.plugin.entity.AiPluginFetcher;
import io.qifan.server.ai.plugin.entity.AiPluginTable;
import io.qifan.server.ai.plugin.entity.dto.AiPluginSpec;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiPluginRepository extends JRepository<AiPlugin, String> {
    AiPluginTable aiPluginTable = AiPluginTable.$;
    AiPluginFetcher COMPLEX_FETCHER_FOR_ADMIN = AiPluginFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    AiPluginFetcher COMPLEX_FETCHER_FOR_FRONT = AiPluginFetcher.$.allScalarFields()
            .creator(true);

    default Page<AiPlugin> findPage(QueryRequest<AiPluginSpec> queryRequest,
                                    Fetcher<AiPlugin> fetcher) {
        AiPluginSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(aiPluginTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(aiPluginTable, pageable.getSort()))
                .select(aiPluginTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}