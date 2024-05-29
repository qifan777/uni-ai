package io.qifan.server.user.wechat.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.user.wechat.entity.UserWeChat;
import io.qifan.server.user.wechat.entity.dto.UserWeChatCreateInput;
import io.qifan.server.user.wechat.entity.dto.UserWeChatSpec;
import io.qifan.server.user.wechat.entity.dto.UserWeChatUpdateInput;
import io.qifan.server.user.wechat.repository.UserWeChatRepository;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/user-we-chat")
@AllArgsConstructor
@DefaultFetcherOwner(UserWeChatRepository.class)
@SaCheckPermission("/user-we-chat")
@Transactional
public class UserWeChatForAdminController {
    private final UserWeChatRepository userWeChatRepository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") UserWeChat findById(@PathVariable String id) {
        return userWeChatRepository.findById(id, UserWeChatRepository.COMPLEX_FETCHER_FOR_ADMIN).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page<@FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") UserWeChat> query(@RequestBody QueryRequest<UserWeChatSpec> queryRequest) {
        return userWeChatRepository.findPage(queryRequest, UserWeChatRepository.COMPLEX_FETCHER_FOR_ADMIN);
    }

    @PostMapping
    public String create(@RequestBody @Validated UserWeChatCreateInput userWeChatInput) {
        return userWeChatRepository.insert(userWeChatInput).id();
    }

    @PutMapping
    public String update(@RequestBody @Validated UserWeChatUpdateInput userWeChatInput) {
        return userWeChatRepository.update(userWeChatInput).id();
    }

    @DeleteMapping
    public void delete(@RequestBody List<String> ids) {
        userWeChatRepository.deleteAllById(ids);
    }
}