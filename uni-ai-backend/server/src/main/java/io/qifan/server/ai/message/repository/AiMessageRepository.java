package io.qifan.server.ai.message.repository;

import io.qifan.server.ai.message.entity.AiMessage;
import io.qifan.server.ai.message.entity.AiMessageFetcher;
import io.qifan.server.ai.message.entity.AiMessageTable;
import io.qifan.server.ai.message.entity.dto.AiMessageSpec;
import io.qifan.server.ai.session.entity.AiSessionFetcher;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiMessageRepository extends JRepository<AiMessage, String> {
    AiMessageTable aiMessageTable = AiMessageTable.$;
    AiMessageFetcher COMPLEX_FETCHER_FOR_ADMIN = AiMessageFetcher.$.allScalarFields()
            .aiSession(AiSessionFetcher.$.allScalarFields())
            .aiSessionId()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    AiMessageFetcher COMPLEX_FETCHER_FOR_FRONT = AiMessageFetcher.$.allScalarFields()
            .creator(true);

    default Page<AiMessage> findPage(QueryRequest<AiMessageSpec> queryRequest,
                                     Fetcher<AiMessage> fetcher) {
        AiMessageSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(aiMessageTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(aiMessageTable, pageable.getSort()))
                .select(aiMessageTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}