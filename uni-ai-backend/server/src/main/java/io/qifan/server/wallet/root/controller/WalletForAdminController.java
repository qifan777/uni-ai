package io.qifan.server.wallet.root.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.root.entity.User;
import io.qifan.server.wallet.record.entity.WalletRecord;
import io.qifan.server.wallet.root.entity.Wallet;
import io.qifan.server.wallet.root.entity.dto.WalletCreateInput;
import io.qifan.server.wallet.root.entity.dto.WalletSpec;
import io.qifan.server.wallet.root.entity.dto.WalletUpdateInput;
import io.qifan.server.wallet.root.repository.WalletRepository;
import io.qifan.server.wallet.root.service.WalletService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.babyfish.jimmer.sql.JSqlClient;
import org.babyfish.jimmer.sql.event.EntityEvent;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/wallet")
@AllArgsConstructor
@DefaultFetcherOwner(WalletRepository.class)
@SaCheckPermission("/wallet")
@Transactional
public class WalletForAdminController {
    private final WalletRepository walletRepository;
    private final JSqlClient jSqlClient;
    private final WalletService walletService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Wallet findById(@PathVariable String id) {
        return walletRepository.findById(id, WalletRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") Wallet> query(@RequestBody QueryRequest<WalletSpec> queryRequest) {
        return walletRepository.findPage(queryRequest, WalletRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated WalletCreateInput walletInput) {
        return walletRepository.insert(walletInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated WalletUpdateInput walletInput) {
        return walletRepository.update(walletInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        walletRepository.deleteAllById(ids);
        return true;
    }

    @PostConstruct
    public void init() {
        jSqlClient.getTriggers().addEntityListener(WalletRecord.class, e -> {
            if (e.getType().equals(EntityEvent.Type.INSERT)) {
                walletService.changeBalance(e);
            }
        });
        jSqlClient.getTriggers().addEntityListener(User.class, e -> {
            if (e.getType().equals(EntityEvent.Type.INSERT)) {
                walletService.createWallet(e);
            }
        });
    }
}