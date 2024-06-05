package io.qifan.server.ai.tag.root.repository;

import io.qifan.server.ai.tag.root.entity.AiTag;
import io.qifan.server.ai.tag.root.entity.AiTagFetcher;
import io.qifan.server.ai.tag.root.entity.AiTagTable;
import io.qifan.server.ai.tag.root.entity.dto.AiTagSpec;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiTagRepository extends JRepository<AiTag, String> {
    AiTagTable aiTagTable = AiTagTable.$;
    AiTagFetcher COMPLEX_FETCHER_FOR_ADMIN = AiTagFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    AiTagFetcher COMPLEX_FETCHER_FOR_FRONT = AiTagFetcher.$.allScalarFields()
            .creator(true);

    default Page<AiTag> findPage(QueryRequest<AiTagSpec> queryRequest,
                                 Fetcher<AiTag> fetcher) {
        AiTagSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(aiTagTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(aiTagTable, pageable.getSort()))
                .select(aiTagTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}