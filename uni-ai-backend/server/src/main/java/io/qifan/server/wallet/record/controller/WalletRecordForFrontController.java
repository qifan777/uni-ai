
package io.qifan.server.wallet.record.controller;
import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.wallet.record.entity.WalletRecord;
import io.qifan.server.wallet.record.entity.dto.WalletRecordCreateInput;
import io.qifan.server.wallet.record.entity.dto.WalletRecordSpec;
import io.qifan.server.wallet.record.entity.dto.WalletRecordUpdateInput;
import io.qifan.server.wallet.record.repository.WalletRecordRepository;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/wallet-record")
@AllArgsConstructor
@DefaultFetcherOwner(WalletRecordRepository.class)
@Transactional
@SaCheckDisable
public class WalletRecordForFrontController {
    private final WalletRecordRepository walletRecordRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") WalletRecord findById(@PathVariable String id) {
        return walletRecordRepository.findById(id, WalletRecordRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page< @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") WalletRecord> query(@RequestBody QueryRequest<WalletRecordSpec> queryRequest) {
    queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
    return walletRecordRepository.findPage(queryRequest, WalletRecordRepository.COMPLEX_FETCHER_FOR_FRONT);
}

    @PostMapping
    public String create(@RequestBody @Validated WalletRecordCreateInput walletRecordCreateInput) {
        return walletRecordRepository.insert(walletRecordCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated WalletRecordUpdateInput walletRecordUpdateInput) {
        WalletRecord walletRecord = walletRecordRepository.findById(walletRecordUpdateInput.getId(), WalletRecordRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!walletRecord.creator().id().equals(walletRecord.editor().id())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return walletRecordRepository.update(walletRecordUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        walletRecordRepository.findByIds(ids, WalletRecordRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(walletRecord -> {
            if (!walletRecord.creator().id().equals(walletRecord.editor().id())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        walletRecordRepository.deleteAllById(ids);
        return true;
    }
}
