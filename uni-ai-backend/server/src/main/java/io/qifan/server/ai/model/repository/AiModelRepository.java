package io.qifan.server.ai.model.repository;

import io.qifan.server.ai.model.entity.AiModel;
import io.qifan.server.ai.model.entity.AiModelFetcher;
import io.qifan.server.ai.model.entity.AiModelTable;
import io.qifan.server.ai.model.entity.dto.AiModelSpec;
import io.qifan.server.ai.tag.root.entity.AiTagFetcher;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiModelRepository extends JRepository<AiModel, String> {
    AiModelTable aiModelTable = AiModelTable.$;
    AiModelFetcher COMPLEX_FETCHER_FOR_ADMIN = AiModelFetcher.$.allScalarFields()
            .options()
            .tagsView(AiTagFetcher.$.name())
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    AiModelFetcher COMPLEX_FETCHER_FOR_FRONT = AiModelFetcher.$.allScalarFields()
            .creator(true);

    default Page<AiModel> findPage(QueryRequest<AiModelSpec> queryRequest,
                                   Fetcher<AiModel> fetcher) {
        AiModelSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(aiModelTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(aiModelTable, pageable.getSort()))
                .select(aiModelTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}