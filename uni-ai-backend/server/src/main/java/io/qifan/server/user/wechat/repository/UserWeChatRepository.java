package io.qifan.server.user.wechat.repository;

import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.wechat.entity.UserWeChat;
import io.qifan.server.user.wechat.entity.UserWeChatFetcher;
import io.qifan.server.user.wechat.entity.UserWeChatTable;
import io.qifan.server.user.wechat.entity.dto.UserWeChatSpec;
import org.babyfish.jimmer.spring.repository.JRepository;
import org.babyfish.jimmer.spring.repository.SpringOrders;
import org.babyfish.jimmer.spring.repository.support.SpringPageFactory;
import org.babyfish.jimmer.sql.fetcher.Fetcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserWeChatRepository extends JRepository<UserWeChat, String> {

    UserWeChatTable userWeChatTable = UserWeChatTable.$;
    UserWeChatFetcher COMPLEX_FETCHER_FOR_ADMIN = UserWeChatFetcher.$.allScalarFields();
    UserWeChatFetcher COMPLEX_FETCHER_FOR_FRONT = UserWeChatFetcher.$.allScalarFields();

    default Page<UserWeChat> findPage(QueryRequest<UserWeChatSpec> queryRequest,
                                      Fetcher<UserWeChat> fetcher) {
        UserWeChatSpec query = queryRequest.getQuery();
        Pageable pageable = queryRequest.toPageable();
        return sql().createQuery(userWeChatTable)
                .where(query)
                .orderBy(SpringOrders.toOrders(userWeChatTable, pageable.getSort()))
                .select(userWeChatTable.fetch(fetcher))
                .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
                        SpringPageFactory.getInstance());
    }
}