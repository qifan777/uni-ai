package io.qifan.server.wallet.item.service;

import io.qifan.server.wallet.item.repository.WalletItemRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class WalletItemService {
    private final WalletItemRepository walletItemRepository;

}