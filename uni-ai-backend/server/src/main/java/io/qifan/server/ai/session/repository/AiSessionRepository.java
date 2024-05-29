package io.qifan.server.ai.session.repository;

import io.qifan.server.ai.message.entity.AiMessageFetcher;
import io.qifan.server.ai.model.entity.AiModelFetcher;
import io.qifan.server.ai.role.entity.AiRoleFetcher;
import io.qifan.server.ai.session.entity.AiSession;
import io.qifan.server.ai.session.entity.AiSessionFetcher;
import io.qifan.server.ai.session.entity.AiSessionTable;
import io.qifan.server.ai.session.entity.dto.AiSessionSpec;
import io.qifan.server.ai.tag.root.entity.AiTagFetcher;
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
            .aiRole(AiRoleFetcher.$.allScalarFields())
            .aiRoleId()
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    AiModelFetcher aiModelFetcher = AiModelFetcher.$.allScalarFields()
            .tagsView(AiTagFetcher.$.allScalarFields());
    AiSessionFetcher COMPLEX_FETCHER_FOR_FRONT = AiSessionFetcher.$
            .allScalarFields()
            .aiRole(AiRoleFetcher.$.allScalarFields().aiModel(aiModelFetcher))
            .aiModel(aiModelFetcher)
            .aiRoleId()
            .aiModelId()
            .messages(AiMessageFetcher.$.allScalarFields())
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