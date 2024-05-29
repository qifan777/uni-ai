
package io.qifan.server.wallet.root.controller;

import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.stp.StpUtil;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.wallet.root.entity.Wallet;
import io.qifan.server.wallet.root.entity.WalletTable;
import io.qifan.server.wallet.root.entity.dto.WalletCreateInput;
import io.qifan.server.wallet.root.entity.dto.WalletSpec;
import io.qifan.server.wallet.root.entity.dto.WalletUpdateInput;
import io.qifan.server.wallet.root.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/wallet")
@AllArgsConstructor
@DefaultFetcherOwner(WalletRepository.class)
@Transactional
@SaCheckDisable
public class WalletForFrontController {
    private final WalletRepository walletRepository;

    @GetMapping
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Wallet get() {
        WalletTable t = WalletTable.$;
        return walletRepository.sql().createQuery(t)
                .where(t.userId().eq(StpUtil.getLoginIdAsString()))
                .select(t.fetch(WalletRepository.COMPLEX_FETCHER_FOR_FRONT))
                .fetchOptional()
                .orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Wallet findById(@PathVariable String id) {
        Wallet wallet = walletRepository.findById(id, WalletRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!wallet.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能查询自己的数据");
        }
        return wallet;
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") Wallet> query(@RequestBody QueryRequest<WalletSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return walletRepository.findPage(queryRequest, WalletRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping
    public String create(@RequestBody @Validated WalletCreateInput walletCreateInput) {
        return walletRepository.insert(walletCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated WalletUpdateInput walletUpdateInput) {
        Wallet wallet = walletRepository.findById(walletUpdateInput.getId(), WalletRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!wallet.creator().id().equals(wallet.editor().id())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return walletRepository.update(walletUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        walletRepository.findByIds(ids, WalletRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(wallet -> {
            if (!wallet.creator().id().equals(wallet.editor().id())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        walletRepository.deleteAllById(ids);
        return true;
    }
}
