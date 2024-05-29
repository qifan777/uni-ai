package io.qifan.server.wallet.item.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.Table;

import java.math.BigDecimal;


/**
 * <p>
 * 钱包充值的可选项
 *
 * </p>
 *
 * @author 起凡
 * @date 2024-04-27
 */
@Entity
@Table(name = "wallet_item")
@GenEntity
public interface WalletItem extends BaseEntity {

    /**
     * 充值项名称
     */
    @GenField(value = "充值项名称", order = 0)
    String name();

    /**
     * 购买后得到的金额
     */
    @GenField(value = "购买后得到的金额", type = ItemType.INPUT_NUMBER, order = 1)
    BigDecimal amount();

    /**
     * 售卖价格
     */
    @GenField(value = "售卖价格", type = ItemType.INPUT_NUMBER, order = 2)
    BigDecimal price();

}
