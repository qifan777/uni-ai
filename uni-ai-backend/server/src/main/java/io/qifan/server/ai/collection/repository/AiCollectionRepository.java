package io.qifan.server.ai.collection.repository;

import io.qifan.server.ai.collection.entity.AiCollection;
import io.qifan.server.ai.collection.entity.AiCollectionFetcher;
import io.qifan.server.ai.collection.entity.AiCollectionTable;
import io.qifan.server.ai.collection.entity.dto.AiCollectionSpec;
import io.qifan.server.ai.model.entity.AiModelFetcher;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiCollectionRepository extends JRepository<AiCollection, String> {
    AiCollectionTable aiCollectionTable = AiCollectionTable.$;
    AiCollectionFetcher COMPLEX_FETCHER_FOR_ADMIN = AiCollectionFetcher.$.allScalarFields()
            .embeddingModel(AiModelFetcher.$.allScalarFields())
            .embeddingModelId()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    AiCollectionFetcher COMPLEX_FETCHER_FOR_FRONT = AiCollectionFetcher.$.allScalarFields()
            .creator(true);

    default Page<AiCollection> findPage(QueryRequest<AiCollectionSpec> queryRequest,
                                        Fetcher<AiCollection> fetcher) {
        AiCollectionSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(aiCollectionTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(aiCollectionTable, pageable.getSort()))
                .select(aiCollectionTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}