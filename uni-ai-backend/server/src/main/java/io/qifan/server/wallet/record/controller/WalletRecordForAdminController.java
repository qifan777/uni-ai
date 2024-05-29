package io.qifan.server.wallet.record.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.dict.model.DictConstants;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.wallet.record.entity.WalletRecord;
import io.qifan.server.wallet.record.entity.WalletRecordTable;
import io.qifan.server.wallet.record.entity.dto.WalletRecordCreateInput;
import io.qifan.server.wallet.record.entity.dto.WalletRecordSpec;
import io.qifan.server.wallet.record.entity.dto.WalletRecordUpdateInput;
import io.qifan.server.wallet.record.repository.WalletRecordRepository;
import io.qifan.server.wallet.record.service.WalletRecordService;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.babyfish.jimmer.sql.ast.tuple.Tuple2;
import org.babyfish.jimmer.sql.ast.tuple.Tuple3;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/wallet-record")
@AllArgsConstructor
@DefaultFetcherOwner(WalletRecordRepository.class)
@SaCheckPermission("/wallet-record")
@Transactional
public class WalletRecordForAdminController {
    private final WalletRecordRepository walletRecordRepository;
    private final WalletRecordService walletRecordService;
    private final JdbcTemplate jdbcTemplate;


    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") WalletRecord findById(@PathVariable String id) {
        return walletRecordRepository.findById(id, WalletRecordRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") WalletRecord> query(@RequestBody QueryRequest<WalletRecordSpec> queryRequest) {
        return walletRecordRepository.findPage(queryRequest, WalletRecordRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated WalletRecordCreateInput walletRecordInput) {
        return walletRecordService.create(walletRecordInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated WalletRecordUpdateInput walletRecordInput) {
        return walletRecordRepository.update(walletRecordInput).id();
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        walletRecordRepository.deleteAllById(ids);
        return true;
    }

    @PostMapping("user/group")
    public Page<Tuple3<String, String, Long>> groupUser(@RequestBody QueryRequest<WalletRecordSpec> queryRequest) {
        WalletRecordTable t = WalletRecordTable.$;
        List<Tuple3<String, String, Long>> execute = walletRecordRepository.sql().createQuery(t)
                .where(queryRequest.getQuery())
                .groupBy(t.wallet().creator().phone(), t.wallet().creator().nickname())
                .select(t.wallet().creator().phone(), t.wallet().creator().nickname(), t.id().count())
                .execute();
        return new PageImpl<>(execute.subList(Math.min((queryRequest.getPageNum() - 1) * queryRequest.getPageSize(), execute.size()
        ), Math.min(queryRequest.getPageNum() * queryRequest.getPageSize(), execute.size())), queryRequest.toPageable(), execute.size());
    }

    @PostMapping("type/group")
    public List<Tuple2<DictConstants.WalletRecordType, Long>> groupByType() {
        WalletRecordTable t = WalletRecordTable.$;
        return walletRecordRepository.sql().createQuery(t)
                .groupBy(t.type())
                .select(t.type(), t.id().count())
                .execute();
    }
}