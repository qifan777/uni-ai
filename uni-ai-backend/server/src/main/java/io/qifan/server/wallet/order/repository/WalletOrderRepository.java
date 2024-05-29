package io.qifan.server.wallet.order.repository;

import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import io.qifan.server.wallet.order.entity.WalletOrder;
import io.qifan.server.wallet.order.entity.WalletOrderFetcher;
import io.qifan.server.wallet.order.entity.WalletOrderTable;
import io.qifan.server.wallet.order.entity.dto.WalletOrderSpec;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletOrderRepository extends JRepository<WalletOrder, String> {
    WalletOrderTable walletOrderTable = WalletOrderTable.$;
    WalletOrderFetcher COMPLEX_FETCHER_FOR_ADMIN = WalletOrderFetcher.$.allScalarFields()
            .userId()
            .user(UserFetcher.$.phone().nickname())
            .creator(UserFetcher.$.phone().nickname())
            .editor(UserFetcher.$.phone().nickname());
    WalletOrderFetcher COMPLEX_FETCHER_FOR_FRONT = WalletOrderFetcher.$.allScalarFields()
            .creator(true);

    default Page<WalletOrder> findPage(QueryRequest<WalletOrderSpec> queryRequest,
                                       Fetcher<WalletOrder> fetcher) {
        WalletOrderSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(walletOrderTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(walletOrderTable, pageable.getSort()))
                .select(walletOrderTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}