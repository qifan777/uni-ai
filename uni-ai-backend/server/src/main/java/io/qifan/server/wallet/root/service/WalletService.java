package io.qifan.server.wallet.root.service;

import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.user.root.entity.User;
import io.qifan.server.wallet.record.entity.WalletRecord;
import io.qifan.server.wallet.root.entity.Wallet;
import io.qifan.server.wallet.root.entity.WalletDraft;
import io.qifan.server.wallet.root.repository.WalletRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.babyfish.jimmer.sql.event.EntityEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class WalletService {
    private final WalletRepository walletRepository;

    public void changeBalance(EntityEvent<WalletRecord> entityEvent) {
        if (!entityEvent.getType().equals(EntityEvent.Type.INSERT)) return;
        WalletRecord walletRecord = entityEvent.getNewEntity();
        Wallet wallet = walletRepository.findById(walletRecord.walletId()).orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "数据不存在"));
        walletRepository.save(WalletDraft.$.produce(wallet,
                draft -> {
                    draft.setBalance(draft.balance().add(walletRecord.amount()));
                    if (draft.balance().compareTo(BigDecimal.ZERO) < 0) {
                        throw new BusinessException("余额不足");
                    }
                }));
    }

    public void createWallet(EntityEvent<User> e) {
        String id = e.getNewEntity().id();
        StpUtil.switchTo(id);
        walletRepository.insert(WalletDraft.$.produce(draft -> {
            draft
                    .setId(id)
                    .setBalance(BigDecimal.ZERO)
                    .setUserId(id)
                    .setPassword("1234566");
        }));
    }
}