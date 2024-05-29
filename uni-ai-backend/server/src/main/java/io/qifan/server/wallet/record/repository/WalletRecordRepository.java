package io.qifan.server.wallet.record.repository;

import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import io.qifan.server.wallet.record.entity.WalletRecord;
import io.qifan.server.wallet.record.entity.WalletRecordFetcher;
import io.qifan.server.wallet.record.entity.WalletRecordTable;
import io.qifan.server.wallet.record.entity.dto.WalletRecordSpec;
import io.qifan.server.wallet.root.entity.WalletFetcher;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletRecordRepository extends JRepository<WalletRecord, String> {
    WalletRecordTable walletRecordTable = WalletRecordTable.$;
    WalletRecordFetcher COMPLEX_FETCHER_FOR_ADMIN = WalletRecordFetcher.$.allScalarFields()
            .wallet(WalletFetcher.$.allScalarFields())
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    WalletRecordFetcher COMPLEX_FETCHER_FOR_FRONT = WalletRecordFetcher.$.allScalarFields()
            .creator(true);

    default Page<WalletRecord> findPage(QueryRequest<WalletRecordSpec> queryRequest,
                                        Fetcher<WalletRecord> fetcher) {
        WalletRecordSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(walletRecordTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(walletRecordTable, pageable.getSort()))
                .select(walletRecordTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}