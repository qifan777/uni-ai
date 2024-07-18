package io.qifan.server.wallet.order.controller;

import cn.dev33.satoken.annotation.SaCheckDisable;
import cn.dev33.satoken.stp.StpUtil;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.wallet.order.entity.WalletOrder;
import io.qifan.server.wallet.order.entity.dto.WalletOrderCreateInput;
import io.qifan.server.wallet.order.entity.dto.WalletOrderSpec;
import io.qifan.server.wallet.order.entity.dto.WalletOrderUpdateInput;
import io.qifan.server.wallet.order.repository.WalletOrderRepository;
import io.qifan.server.wallet.order.service.WalletOrderService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.ApiIgnore;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("front/wallet-order")
@AllArgsConstructor
@DefaultFetcherOwner(WalletOrderRepository.class)
@Transactional
@SaCheckDisable
public class WalletOrderForFrontController {
    private final WalletOrderRepository walletOrderRepository;
    private final WalletOrderService walletOrderService;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") WalletOrder findById(@PathVariable String id) {
        return walletOrderRepository.findById(id, WalletOrderRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") WalletOrder> query(@RequestBody QueryRequest<WalletOrderSpec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return walletOrderRepository.findPage(queryRequest, WalletOrderRepository.COMPLEX_FETCHER_FOR_FRONT);
    }

    @PostMapping
    public String create(@RequestBody @Validated WalletOrderCreateInput walletOrderCreateInput) {
        return walletOrderRepository.insert(walletOrderCreateInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated WalletOrderUpdateInput walletOrderUpdateInput) {
        WalletOrder walletOrder = walletOrderRepository.findById(walletOrderUpdateInput.getId(), WalletOrderRepository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!walletOrder.creator().id().equals(walletOrder.editor().id())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return walletOrderRepository.update(walletOrderUpdateInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        walletOrderRepository.findByIds(ids, WalletOrderRepository.COMPLEX_FETCHER_FOR_FRONT).forEach(walletOrder -> {
            if (!walletOrder.creator().id().equals(walletOrder.editor().id())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        walletOrderRepository.deleteAllById(ids);
        return true;
    }

    @PostMapping("recharge")
    public WxPayUnifiedOrderV3Result.JsapiResult recharge(@RequestParam String id) {
        return walletOrderService.recharge(id);
    }

    @PostMapping("notify")
    @ApiIgnore
    public String paymentNotifyWechat(@RequestBody String body,
                                      @RequestHeader(value = "Wechatpay-Timestamp") String timestamp,
                                      @RequestHeader(value = "Wechatpay-Nonce") String nonce,
                                      @RequestHeader(value = "Wechatpay-Signature") String signature,
                                      @RequestHeader(value = "Wechatpay-Serial") String serial) {
        SignatureHeader signatureHeader = SignatureHeader.builder().signature(signature)
                .serial(serial)
                .nonce(nonce)
                .timeStamp(timestamp).build();
        return walletOrderService.rechargeNotify(body, signatureHeader);
    }
}
