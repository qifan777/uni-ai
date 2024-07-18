package io.qifan.server.wallet.order.service;

import cn.dev33.satoken.stp.StpUtil;
import com.binarywang.spring.starter.wxjava.pay.properties.WxPayProperties;
import com.github.binarywang.wxpay.bean.notify.SignatureHeader;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.service.WxPayService;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.infrastructure.model.WxPayPropertiesExtension;
import io.qifan.server.user.wechat.entity.UserWeChatTable;
import io.qifan.server.user.wechat.repository.UserWeChatRepository;
import io.qifan.server.wallet.item.entity.WalletItem;
import io.qifan.server.wallet.item.entity.dto.WalletItemView;
import io.qifan.server.wallet.item.repository.WalletItemRepository;
import io.qifan.server.wallet.order.entity.WalletOrder;
import io.qifan.server.wallet.order.entity.WalletOrderDraft;
import io.qifan.server.wallet.order.repository.WalletOrderRepository;
import io.qifan.server.wallet.record.entity.dto.WalletRecordCreateInput;
import io.qifan.server.wallet.record.service.WalletRecordService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class WalletOrderService {
    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
    private final WxPayService wxPayService;
    private final WalletOrderRepository walletOrderRepository;
    private final WxPayProperties wxPayProperties;
    private final UserWeChatRepository userWeChatRepository;
    private final WalletItemRepository walletItemRepository;
    private final WalletRecordService walletRecordService;
    private final WxPayPropertiesExtension wxPayPropertiesExtension;

    @SneakyThrows
    public WxPayUnifiedOrderV3Result.JsapiResult recharge(String id) {
        WalletItem walletItem = walletItemRepository.findById(id).orElseThrow(() -> new RuntimeException("充值项不存在"));
        WalletOrder walletOrder = walletOrderRepository.save(WalletOrderDraft.$.produce(draft -> {
            draft.setUserId(StpUtil.getLoginIdAsString())
                    .setAmount(walletItem.price())
                    .setWalletItem(new WalletItemView(walletItem));
        }));
        return wxPayService.createOrderV3(TradeTypeEnum.JSAPI, create(walletOrder));
    }

    @SneakyThrows
    public String rechargeNotify(String body, SignatureHeader signatureHeader) {
        WxPayNotifyV3Result.DecryptNotifyResult notifyResult = wxPayService.parseOrderNotifyV3Result(body, signatureHeader)
                .getResult();
        log.info("支付回调:{}", notifyResult);
        WalletOrder walletOrder = walletOrderRepository.findById(notifyResult.getOutTradeNo()).orElseThrow(() -> new BusinessException("订单不存在"));
        StpUtil.switchTo(walletOrder.userId());
        WalletRecordCreateInput walletRecordCreateInput = new WalletRecordCreateInput.Builder()
                .amount(walletOrder.walletItem().getAmount())
                .walletId(StpUtil.getLoginIdAsString())
                .description(walletOrder.walletItem().getName())
                .type(DictConstants.WalletRecordType.RECHARGE)
                .build();
        walletRecordService.create(walletRecordCreateInput);
        return "SUCCESS";
    }


    public WxPayUnifiedOrderV3Request create(WalletOrder walletOrder) {
        WxPayUnifiedOrderV3Request wxPayUnifiedOrderV3Request = new WxPayUnifiedOrderV3Request();
        // 支付价格
        WxPayUnifiedOrderV3Request.Amount amount = new WxPayUnifiedOrderV3Request.Amount();
        amount.setTotal(walletOrder.amount()
                .multiply(BigDecimal.valueOf(
                        100)).intValue());
        // 获取支付人信息
        WxPayUnifiedOrderV3Request.Payer payer = new WxPayUnifiedOrderV3Request.Payer();
        UserWeChatTable t = UserWeChatTable.$;
        String openid = userWeChatRepository.sql().createQuery(t)
                .where(t.userId().eq(StpUtil.getLoginIdAsString()))
                .select(t.openId())
                .fetchOptional()
                .orElseThrow(() -> new RuntimeException("非小程序用户"));
        payer.setOpenid(openid);
        wxPayUnifiedOrderV3Request.setPayer(payer)
                .setAmount(amount)
                // 回调地址
                .setNotifyUrl(wxPayPropertiesExtension.getNotifyUrl() + "/front/wallet-order/notify")
                // 小程序appid
                .setAppid(wxPayProperties.getAppId())
                // 商家号
                .setMchid(wxPayProperties.getMchId())
                // 支付描述
                .setDescription(walletOrder.walletItem().getName())
                // 订单系统的订单号
                .setOutTradeNo(walletOrder.id())
                // 过期时间
                .setTimeExpire(dateTimeFormatter.format(ZonedDateTime.now().plusMinutes(5)));
        return wxPayUnifiedOrderV3Request;
    }
}