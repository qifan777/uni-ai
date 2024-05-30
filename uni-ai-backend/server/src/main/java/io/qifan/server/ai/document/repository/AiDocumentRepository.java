package io.qifan.server.ai.document.repository;

import io.qifan.server.ai.collection.entity.AiCollectionFetcher;
import io.qifan.server.ai.document.entity.AiDocument;
import io.qifan.server.ai.document.entity.AiDocumentFetcher;
import io.qifan.server.ai.document.entity.AiDocumentTable;
import io.qifan.server.ai.document.entity.dto.AiDocumentSpec;
import io.qifan.server.ai.model.entity.AiModelFetcher;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AiDocumentRepository extends JRepository<AiDocument, String> {
    AiDocumentTable aiDocumentTable = AiDocumentTable.$;
    AiDocumentFetcher COMPLEX_FETCHER_FOR_ADMIN = AiDocumentFetcher.$.allScalarFields()
            .aiCollectionId()
            .aiCollection(AiCollectionFetcher.$.allScalarFields())
            .summaryModelId()
            .summaryModel(AiModelFetcher.$.allScalarFields())
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    AiDocumentFetcher COMPLEX_FETCHER_FOR_FRONT = AiDocumentFetcher.$.allScalarFields()
            .creator(true);

    default Page<AiDocument> findPage(QueryRequest<AiDocumentSpec> queryRequest,
                                      Fetcher<AiDocument> fetcher) {
        AiDocumentSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(aiDocumentTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(aiDocumentTable, pageable.getSort()))
                .select(aiDocumentTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}