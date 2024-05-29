package io.qifan.server.menu.controller;

import io.qifan.server.infrastructure.model.QueryRequest;
import io.qifan.server.menu.entity.Menu;
import io.qifan.server.menu.entity.dto.MenuInput;
import io.qifan.server.menu.entity.dto.MenuSpec;
import io.qifan.server.menu.repository.MenuRepository;
import io.qifan.server.menu.service.MenuService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.babyfish.jimmer.client.FetchBy;
import org.babyfish.jimmer.client.meta.DefaultFetcherOwner;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("menu")
@AllArgsConstructor
@DefaultFetcherOwner(MenuRepository.class)
public class MenuController {

  private final MenuService menuService;

  @GetMapping("{id}")
  public @FetchBy(value = "COMPLEX_FETCHER") Menu findById(@PathVariable String id) {
    return menuService.findById(id);
  }

  @PostMapping("query")
  public Page<@FetchBy(value = "COMPLEX_FETCHER") Menu> query(
      @RequestBody QueryRequest<MenuSpec> queryRequest) {
    return menuService.query(queryRequest);
  }

  @PostMapping("save")
  public String save(@RequestBody @Validated MenuInput menu) {
    return menuService.save(menu);
  }

  @PostMapping("delete")
  public Boolean delete(@RequestBody List<String> ids) {
    return menuService.delete(ids);
  }
}