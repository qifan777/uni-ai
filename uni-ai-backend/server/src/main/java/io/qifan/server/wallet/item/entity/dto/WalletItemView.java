package io.qifan.server.wallet.item.entity.dto;

import io.qifan.infrastructure.generator.core.GenEntity;
import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.server.wallet.item.entity.WalletItem;
import io.qifan.server.wallet.item.entity.WalletItemDraft;
import io.qifan.server.wallet.item.entity.WalletItemFetcher;
import org.babyfish.jimmer.View;
import org.babyfish.jimmer.internal.GeneratedBy;
import org.babyfish.jimmer.sql.fetcher.ViewMetadata;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 钱包充值的可选项
 *
 * </p>
 *
 * @author 起凡
 * @date 2024-04-27
 */
@GeneratedBy(
        file = "<server>/src/main/dto/wallet/WalletItem.dto"
)
@GenEntity
public class WalletItemView implements View<WalletItem> {
    public static final ViewMetadata<WalletItem, WalletItemView> METADATA =
            new ViewMetadata<WalletItem, WalletItemView>(
                    WalletItemFetcher.$
                            .createdTime()
                            .editedTime()
                            .name()
                            .amount()
                            .price(),
                    WalletItemView::new
            );

    private String id;

    private LocalDateTime createdTime;

    private LocalDateTime editedTime;

    private String name;

    private BigDecimal amount;

    private BigDecimal price;

    public WalletItemView() {
    }

    public WalletItemView(@NotNull WalletItem base) {
        this.id = base.id();
        this.createdTime = base.createdTime();
        this.editedTime = base.editedTime();
        this.name = base.name();
        this.amount = base.amount();
        this.price = base.price();
    }

    @NotNull
    public String getId() {
        if (id == null) {
            throw new IllegalStateException("The property \"id\" is not specified");
        }
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    @NotNull
    public LocalDateTime getCreatedTime() {
        if (createdTime == null) {
            throw new IllegalStateException("The property \"createdTime\" is not specified");
        }
        return createdTime;
    }

    public void setCreatedTime(@NotNull LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @NotNull
    public LocalDateTime getEditedTime() {
        if (editedTime == null) {
            throw new IllegalStateException("The property \"editedTime\" is not specified");
        }
        return editedTime;
    }

    public void setEditedTime(@NotNull LocalDateTime editedTime) {
        this.editedTime = editedTime;
    }

    /**
     * 充值项名称
     */
    @NotNull
    @GenField(
            value = "充值项名称",
            order = 0
    )
    public String getName() {
        if (name == null) {
            throw new IllegalStateException("The property \"name\" is not specified");
        }
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    /**
     * 购买后得到的金额
     */
    @NotNull
    @GenField(
            value = "购买后得到的金额",
            type = ItemType.INPUT_NUMBER,
            order = 1
    )
    public BigDecimal getAmount() {
        if (amount == null) {
            throw new IllegalStateException("The property \"amount\" is not specified");
        }
        return amount;
    }

    public void setAmount(@NotNull BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 售卖价格
     */
    @NotNull
    @GenField(
            value = "售卖价格",
            type = ItemType.INPUT_NUMBER,
            order = 2
    )
    public BigDecimal getPrice() {
        if (price == null) {
            throw new IllegalStateException("The property \"price\" is not specified");
        }
        return price;
    }

    public void setPrice(@NotNull BigDecimal price) {
        this.price = price;
    }

    @Override
    public WalletItem toEntity() {
        return WalletItemDraft.$.produce(__draft -> {
            __draft.setId(id);
            __draft.setCreatedTime(createdTime);
            __draft.setEditedTime(editedTime);
            __draft.setName(name);
            __draft.setAmount(amount);
            __draft.setPrice(price);
        });
    }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(id);
        hash = hash * 31 + Objects.hashCode(createdTime);
        hash = hash * 31 + Objects.hashCode(editedTime);
        hash = hash * 31 + Objects.hashCode(name);
        hash = hash * 31 + Objects.hashCode(amount);
        hash = hash * 31 + Objects.hashCode(price);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        WalletItemView other = (WalletItemView) o;
        if (!Objects.equals(id, other.id)) {
            return false;
        }
        if (!Objects.equals(createdTime, other.createdTime)) {
            return false;
        }
        if (!Objects.equals(editedTime, other.editedTime)) {
            return false;
        }
        if (!Objects.equals(name, other.name)) {
            return false;
        }
        if (!Objects.equals(amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(price, other.price)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("WalletItemView").append('(');
        builder.append("id=").append(id);
        builder.append(", createdTime=").append(createdTime);
        builder.append(", editedTime=").append(editedTime);
        builder.append(", name=").append(name);
        builder.append(", amount=").append(amount);
        builder.append(", price=").append(price);
        builder.append(')');
        return builder.toString();
    }
}
