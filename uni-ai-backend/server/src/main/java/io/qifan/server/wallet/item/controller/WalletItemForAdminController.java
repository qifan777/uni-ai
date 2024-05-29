package io.qifan.server.wallet.item.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.wallet.item.entity.WalletItem;
import io.qifan.server.wallet.item.entity.dto.WalletItemCreateInput;
import io.qifan.server.wallet.item.entity.dto.WalletItemSpec;
import io.qifan.server.wallet.item.entity.dto.WalletItemUpdateInput;
import io.qifan.server.wallet.item.repository.WalletItemRepository;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/wallet-item")
@AllArgsConstructor
@DefaultFetcherOwner(WalletItemRepository.class)
@SaCheckPermission("/wallet-item")
@Transactional
public class WalletItemForAdminController {
    private final WalletItemRepository walletItemRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") WalletItem findById(@PathVariable String id) {
        return walletItemRepository.findById(id, WalletItemRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") WalletItem> query(@RequestBody QueryRequest<WalletItemSpec> queryRequest) {
        return walletItemRepository.findPage(queryRequest, WalletItemRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated WalletItemCreateInput walletItemInput) {
        return walletItemRepository.insert(walletItemInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated WalletItemUpdateInput walletItemInput) {
        return walletItemRepository.update(walletItemInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        walletItemRepository.deleteAllById(ids);
        return true;
    }
}