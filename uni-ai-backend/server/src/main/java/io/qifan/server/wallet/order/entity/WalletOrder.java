package io.qifan.server.wallet.order.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import io.qifan.server.user.root.entity.User;
import io.qifan.server.wallet.item.entity.dto.WalletItemView;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * <p>
 * 钱包充值订单
 *
 * </p>
 *
 * @author 起凡
 * @date 2024-04-27
 */
@Entity
@Table(name = "wallet_order")
@GenEntity
public interface WalletOrder extends BaseEntity {

    /**
     * 支付金额
     */
    @GenField(value = "支付金额", order = 0, type = ItemType.INPUT_NUMBER)
    BigDecimal amount();

    /**
     * 支付时间
     */
    @GenField(value = "支付时间", order = 1, type = ItemType.DATETIME)
    @Column(name = "pay_time")
    @Nullable
    LocalDateTime payTime();

    /**
     * 用户id
     */
    @GenField(value = "用户", order = 2, type = ItemType.USER_SELECT)
    @IdView
    String userId();

    @ManyToOne
    User user();

    /**
     * 充值的钱包可选项详情
     */
    @GenField(value = "充值的钱包可选项详情", order = 3)
    @Column(name = "wallet_item")
    @Serialized
    WalletItemView walletItem();

}
