<script lang="tsx">
import { defineComponent, h, resolveComponent } from 'vue'
import { ElIcon, ElMenu, ElMenuItem, ElSubMenu, ElPopover } from 'element-plus'
import { useHomeStore } from '@/stores/home-store'
import type { MenuTreeDto } from '@/typings'
import { useRoute } from 'vue-router'
import router from '@/router'
import { useTagStore } from '@/layout/store/tag-store'
export default defineComponent({
  components: {},
  props: {
    // 控制是否展开
    collapse: {
      type: Boolean
    }
  },
  setup(props) {
    // 获取全局缓存的菜单树
    const homeStore = useHomeStore()
    const tagStore = useTagStore()
    // tsx递归生成菜单树
    const subMenuList = (menuList: MenuTreeDto[], depth: number) => {
      menuList = menuList.sort((a, b) => (a.orderNum ?? 999) - (b.orderNum ?? 999))
      if (!menuList) return
      return menuList.map((menu) => {
        // 菜单类型是目录则用ElSubMenu，并且继续递归向下生成子菜单。
        if (menu.menuType === 'DIRECTORY') {
          return (
            <ElSubMenu class={depth === 0 ? 'root' : ''} key={menu.path} index={menu.path}>
              {{
                title: () => {
                  // 加载动态的图标
                  return [
                    menu.icon ? <ElIcon size={16}>{h(resolveComponent(menu.icon))}</ElIcon> : '',
                    <span>{menu.name}</span>
                  ]
                },
                default: () => subMenuList(menu.children || [], depth + 1)
              }}
            </ElSubMenu>
          )
        } else if (menu.menuType === 'PAGE') {
          // 菜单类型是页面用ElMenuItem
          return (
            <ElMenuItem
              class={depth === 0 ? 'root' : ''}
              key={menu.path}
              index={menu.path}
              onClick={() => tagStore.openTag(menu.path)}
            >
              {{
                default: () => {
                  return [
                    <ElPopover content={menu.name} hideAfter={0} placement={'right'}>
                      {{
                        reference: () =>
                          menu.icon ? (
                            <ElIcon size={16}>{h(resolveComponent(menu.icon))}</ElIcon>
                          ) : (
                            ''
                          )
                      }}
                    </ElPopover>,
                    <span>{menu.name}</span>
                  ]
                }
              }}
            </ElMenuItem>
          )
        }
        return <div></div>
      })
    }
    return () => (
      <ElMenu collapse={props.collapse} defaultActive={tagStore.activeTag.path}>
        {subMenuList(homeStore.menuTreeList, 0)}
      </ElMenu>
    )
  }
})
</script>

<style lang="scss" scoped>
@mixin item-color {
  .el-icon {
    color: rgb(144, 147, 153);
  }
  &:hover {
    color: #3692eb;
    .el-icon {
      svg {
        :deep(path) {
          fill: #3692eb !important;
        }
      }
    }
  }
}
@mixin item-active {
  background-image: linear-gradient(to left, #5fc3ff, #4a47ff);
  color: white;
  .el-icon {
    color: white;
    svg {
      :deep(path) {
        fill: white !important;
      }
    }
  }
}
@mixin item-basic {
  height: 32px;
  line-height: 32px;
  margin-top: 20px;
  border-radius: 25px;
}
:deep(.el-sub-menu__title) {
  @include item-color;
}
.el-menu-item {
  @include item-color;
}

.el-menu {
  border-right: 0;
  width: 160px;

  :deep(.el-menu) {
    border-radius: 5px;
    background-color: #f5f5f5;
    overflow: hidden;

    .el-menu-item {
      height: 42px;
      line-height: 42px;
      position: relative;
      &.is-active {
        color: #3692eb;
        &::before {
          content: '';
          position: absolute;
          background-color: #3692eb;
          height: 6px;
          width: 6px;
          border-radius: 3px;
          left: 25px;
        }
      }
    }
  }
  .root {
    &.el-menu-item {
      @include item-basic;
      &.is-active {
        @include item-active;
      }
    }

    &.el-sub-menu {
      :deep(.el-sub-menu__title) {
        margin-bottom: 10px;
        @include item-basic;
      }
      &.is-active {
        :deep(.el-sub-menu__title) {
          @include item-active;
        }
      }
    }
  }
  &.el-menu--collapse {
    width: 40px;
    .el-menu-item {
      padding: 8px;
      width: 40px;
    }
    :deep(.el-sub-menu__title) {
      padding: 8px;
      width: 40px;
    }
  }
}
</style>
