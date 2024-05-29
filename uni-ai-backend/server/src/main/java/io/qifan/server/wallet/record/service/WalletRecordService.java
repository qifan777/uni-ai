package io.qifan.server.wallet.record.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.wallet.record.entity.WalletRecord;
import io.qifan.server.wallet.record.entity.WalletRecordDraft;
import io.qifan.server.wallet.record.entity.dto.WalletRecordCreateInput;
import io.qifan.server.wallet.record.repository.WalletRecordRepository;
import io.qifan.server.wallet.root.entity.Wallet;
import io.qifan.server.wallet.root.repository.WalletRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class WalletRecordService {
    private final WalletRecordRepository walletRecordRepository;
    private final WalletRepository walletRepository;

    public WalletRecord create(WalletRecordCreateInput walletRecordCreateInput) {
        Wallet wallet = walletRepository.findById(walletRecordCreateInput.getWalletId())
                .orElseThrow(() -> new BusinessException(ResultCode.NotFindError, "钱包异常"));
        return walletRecordRepository.save(WalletRecordDraft.$.produce(walletRecordCreateInput.toEntity(), draft ->
                draft.setBalance(wallet.balance().add(draft.amount()))));
    }
}