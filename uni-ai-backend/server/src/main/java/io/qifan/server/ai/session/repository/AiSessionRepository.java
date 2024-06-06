package io.qifan.server.ai.session.repository;

import io.qifan.server.ai.message.entity.AiMessageFetcher;
import io.qifan.server.ai.session.entity.AiSession;
import io.qifan.server.ai.session.entity.AiSessionFetcher;
import io.qifan.server.ai.session.entity.AiSessionTable;
import io.qifan.server.ai.session.entity.dto.AiSessionSpec;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiSessionRepository extends JRepository<AiSession, String> {
    AiSessionTable aiSessionTable = AiSessionTable.$;
    AiSessionFetcher COMPLEX_FETCHER_FOR_ADMIN = AiSessionFetcher.$.allScalarFields()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    AiSessionFetcher COMPLEX_FETCHER_FOR_FRONT = AiSessionFetcher.$
            .allScalarFields()
            .messages(AiMessageFetcher.$.aiSessionId().content().createdTime().type())
            .creator(true);

    default Page<AiSession> findPage(QueryRequest<AiSessionSpec> queryRequest,
                                     Fetcher<AiSession> fetcher) {
        AiSessionSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(aiSessionTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(aiSessionTable, pageable.getSort()))
                .select(aiSessionTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}