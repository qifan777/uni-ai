package io.qifan.server.wallet.order.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.wallet.order.entity.WalletOrder;
import io.qifan.server.wallet.order.entity.dto.WalletOrderCreateInput;
import io.qifan.server.wallet.order.entity.dto.WalletOrderSpec;
import io.qifan.server.wallet.order.entity.dto.WalletOrderUpdateInput;
import io.qifan.server.wallet.order.repository.WalletOrderRepository;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/wallet-order")
@AllArgsConstructor
@DefaultFetcherOwner(WalletOrderRepository.class)
@SaCheckPermission("/wallet-order")
@Transactional
public class WalletOrderForAdminController {
    private final WalletOrderRepository walletOrderRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") WalletOrder findById(@PathVariable String id) {
        return walletOrderRepository.findById(id, WalletOrderRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") WalletOrder> query(@RequestBody QueryRequest<WalletOrderSpec> queryRequest) {
        return walletOrderRepository.findPage(queryRequest, WalletOrderRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated WalletOrderCreateInput walletOrderInput) {
        return walletOrderRepository.insert(walletOrderInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated WalletOrderUpdateInput walletOrderInput) {
        return walletOrderRepository.update(walletOrderInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        walletOrderRepository.deleteAllById(ids);
        return true;
    }
}