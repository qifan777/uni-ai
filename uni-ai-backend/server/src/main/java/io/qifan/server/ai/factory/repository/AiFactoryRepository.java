package io.qifan.server.ai.factory.repository;

import io.qifan.server.ai.factory.entity.AiFactory;
import io.qifan.server.ai.factory.entity.AiFactoryFetcher;
import io.qifan.server.ai.factory.entity.AiFactoryTable;
import io.qifan.server.ai.factory.entity.dto.AiFactorySpec;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiFactoryRepository extends JRepository<AiFactory, String> {
    AiFactoryTable aiFactoryTable = AiFactoryTable.$;
    AiFactoryFetcher COMPLEX_FETCHER_FOR_ADMIN = AiFactoryFetcher.$.allScalarFields()
        .creator(UserFetcher.$.phone().nickname())
        .editor(UserFetcher.$.phone().nickname());
    AiFactoryFetcher COMPLEX_FETCHER_FOR_FRONT = AiFactoryFetcher.$.allScalarFields()
            .creator(true);
  default Page<AiFactory> findPage(QueryRequest<AiFactorySpec> queryRequest,
                                   Fetcher<AiFactory> fetcher) {
    AiFactorySpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(aiFactoryTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(aiFactoryTable, pageable.getSort()))
        .select(aiFactoryTable.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}