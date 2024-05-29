package io.qifan.server.wallet.record.entity;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.infrastructure.jimmer.BaseEntity;
import io.qifan.server.wallet.root.entity.Wallet;
import org.babyfish.jimmer.sql.Entity;
import org.babyfish.jimmer.sql.IdView;
import org.babyfish.jimmer.sql.ManyToOne;
import org.babyfish.jimmer.sql.Table;

import java.math.BigDecimal;


/**
 * <p>
 * 钱包流水记录
 *
 * </p>
 *
 * @author 起凡
 * @date 2024-04-27
 */
@Entity
@Table(name = "wallet_record")
@GenEntity
public interface WalletRecord extends BaseEntity {

    /**
     * 钱包id
     */
    @GenField(value = "钱包", order = 0, type = ItemType.ASSOCIATION_SELECT)
    @IdView
    String walletId();

    @ManyToOne
    Wallet wallet();

    /**
     * 金额
     */
    @GenField(value = "操作金额", order = 1, type = ItemType.INPUT_NUMBER)
    BigDecimal amount();

    /**
     * 钱包当前余额
     */
    @GenField(value = "钱包当前余额", order = 2, type = ItemType.INPUT_NUMBER)
    BigDecimal balance();

    /**
     * 类型如：提现，充值，奖励，返佣等等
     */
    @GenField(value = "类型", order = 3, type = ItemType.SELECTABLE, dictEnName = DictConstants.WALLET_RECORD_TYPE)
    DictConstants.WalletRecordType type();

    /**
     * 描述信息
     */
    @GenField(value = "描述信息", order = 4, type = ItemType.INPUT_TEXT_AREA)
    String description();


}
