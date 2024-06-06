
package io.qifan.server.wallet.item.controller;

import cn.dev33.satoken.annotation.SaCheckDisable;
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
@RequestMapping("front/wallet-item")
@AllArgsConstructor
@DefaultFetcherOwner(WalletItemRepository.class)
@Transactional
@SaCheckDisable
public class WalletItemForFrontController {
    private final WalletItemRepository walletItemRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") WalletItem findById(@PathVariable String id) {
        return walletItemRepository.findById(id, WalletItemRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page< @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") WalletItem> query(@RequestBody QueryRequest<WalletItemSpec> queryRequest) {

    return walletItemRepository.findPage(queryRequest, WalletItemRepository.COMPLEX_FETCHER_FOR_FRONT);
}

    @PostMapping
    public String create(@RequestBody @Validated WalletItemCreateInput walletItemCreateInput) {
        return walletItemRepository.insert(walletItemCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated WalletItemUpdateInput walletItemUpdateInput) {
        WalletItem walletItem = walletItemRepository.findById(walletItemUpdateInput.getId(), WalletItemRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!walletItem.creator().id().equals(walletItem.editor().id())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return walletItemRepository.update(walletItemUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        walletItemRepository.findByIds(ids, WalletItemRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(walletItem -> {
            if (!walletItem.creator().id().equals(walletItem.editor().id())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        walletItemRepository.deleteAllById(ids);
        return true;
    }
}
