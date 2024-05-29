package io.qifan.server.role.service;

import io.qifan.infrastructure.common.constants.ResultCode;
import io.qifan.infrastructure.common.exception.BusinessException;
import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.role.entity.Role;
import io.qifan.server.role.entity.RoleDraft;
import io.qifan.server.role.entity.dto.RoleInput;
import io.qifan.server.role.entity.dto.RoleSpec;
import io.qifan.server.role.repository.RoleRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class RoleService {

  private final RoleRepository roleRepository;

  public Role findById(String id) {
    return roleRepository.findById(id, RoleRepository.ROLE_MENU_FETCHER)
        .orElseThrow(() -> new BusinessException(
            ResultCode.NotFindError, "数据不存在"));
  }

  public String save(RoleInput roleInput) {
    Role role = roleInput.toEntity();
    return roleRepository.save(RoleDraft.$.produce(role, draft -> {
      draft.setMenus(new ArrayList<>());
      Arrays.stream(roleInput.getMenuIds()).forEach(menuId -> {
        draft.addIntoMenus(roleMenuRelDraft -> roleMenuRelDraft.setRole(role)
            .applyMenu(menuDraft -> menuDraft.setId(menuId)));
      });
    })).id();
  }

  public Page<Role> query(QueryRequest<RoleSpec> queryRequest) {
    return roleRepository.findPage(queryRequest, RoleRepository.COMPLEX_FETCHER);
  }

  public boolean delete(List<String> ids) {
    roleRepository.deleteAllById(ids);
    return true;
  }
}