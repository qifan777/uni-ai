package io.qifan.server.wallet.root.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import io.qifan.server.user.root.entity.User;
import org.babyfish.jimmer.sql.*;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;


/**
 * <p>
 * 用户钱包
 *
 * </p>
 *
 * @author 起凡
 * @date 2024-04-27
 */
@Entity
@Table(name = "wallet")
@GenEntity
public interface Wallet extends BaseEntity {

    /**
     * 余额
     */
    @GenField(value = "余额", order = 0, type = ItemType.INPUT_NUMBER)
    BigDecimal balance();

    /**
     * 钱包密码
     */
    @Nullable
    @GenField(value = "钱包密码", order = 1)
    String password();

    /**
     * 用户id
     */
    @IdView
    String userId();

    @Key
    @ManyToOne
    @OnDissociate(DissociateAction.DELETE)
    User user();

}
