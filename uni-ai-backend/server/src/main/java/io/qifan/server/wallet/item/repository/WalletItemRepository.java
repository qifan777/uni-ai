package io.qifan.server.wallet.item.repository;

import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.UserFetcher;
import io.qifan.server.wallet.item.entity.WalletItem;
import io.qifan.server.wallet.item.entity.WalletItemFetcher;
import io.qifan.server.wallet.item.entity.WalletItemTable;
import io.qifan.server.wallet.item.entity.dto.WalletItemSpec;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WalletItemRepository extends JRepository<WalletItem, String> {
    WalletItemTable walletItemTable = WalletItemTable.$;
    WalletItemFetcher COMPLEX_FETCHER_FOR_ADMIN = WalletItemFetcher.$.allScalarFields()
        .creator(UserFetcher.$.phone().nickname())
        .editor(UserFetcher.$.phone().nickname());
    WalletItemFetcher COMPLEX_FETCHER_FOR_FRONT = WalletItemFetcher.$.allScalarFields()
            .creator(true);
  default Page<WalletItem> findPage(QueryRequest<WalletItemSpec> queryRequest,
                                    Fetcher<WalletItem> fetcher) {
    WalletItemSpec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(walletItemTable)
        .where(query)
        .orderBy(SpringOrders.toOrders(walletItemTable, pageable.getSort()))
        .select(walletItemTable.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}