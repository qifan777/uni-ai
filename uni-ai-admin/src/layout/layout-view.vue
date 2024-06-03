<script setup lang="ts">
import AsideMenu from '@/layout/components/aside-menu.vue'
import { onMounted, ref, watch } from 'vue'
import { Avatar, Fold, Setting } from '@element-plus/icons-vue'
import { useHomeStore } from '@/stores/home-store'
import { storeToRefs } from 'pinia'
import logo from '@/assets/logo.jpg'
import RouterTags from '@/layout/components/router-tags.vue'
import router from '@/router'
import { useTagStore } from '@/layout/store/tag-store'
const tagStore = useTagStore()
const { collapse } = storeToRefs(tagStore)
const homeStore = useHomeStore()

homeStore.init()
const { userInfo } = storeToRefs(homeStore)
const handleLogout = () => {
  homeStore.logout()
  router.push('/login')
}
onMounted(() => {
  let init = false
  if (tagStore.activeTag.path) {
    console.log(tagStore.activeTag.path)
  }
  watch(
    () => homeStore.menuTreeList.length,
    (value) => {
      if (!init && value > 0) {
        tagStore.openTag(tagStore.activeTag.path)
        init = true
      }
    }
  )
})
</script>

<template>
  <el-container class="index">
    <!-- 对应红色的上半部分，展示页头，用户信息，退出登录等 -->
    <el-header class="header-wrapper">
      <div class="header">
        <div class="logo">
          <el-avatar :src="logo" :size="22"></el-avatar>
          <div class="separator"></div>
          <span>UNI-AI</span>
        </div>
        <div class="flex-grow"></div>
        <div class="username-wrapper" v-if="userInfo">
          <el-icon>
            <avatar></avatar>
          </el-icon>
          <div class="username">{{ userInfo.nickname }}</div>
          <div class="avatar" v-if="userInfo.avatar">
            <el-avatar :src="userInfo.avatar" :size="22"></el-avatar>
          </div>
          <el-dropdown class="dropdown">
            <el-icon>
              <setting></setting>
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleLogout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </el-header>
    <!-- 对应红色的下半部分 -->
    <el-container class="menu-router">
      <!-- 对应蓝色的左半部分，展示侧边菜单 -->
      <div class="aside-menu-wrapper">
        <aside-menu :collapse="collapse"></aside-menu>
      </div>
      <!-- 对应蓝色的右半部分 -->
      <el-main class="router-wrapper">
        <!-- 对应绿色的上半部分，展示页签 -->
        <el-header class="router-header">
          <div @click="collapse = !collapse" class="fold-wrapper">
            <el-icon :class="['fold', collapse ? 'expand' : '']" size="20">
              <fold></fold>
            </el-icon>
          </div>
          <router-tags></router-tags>
        </el-header>
        <!-- 对应绿色的下半部分，展示子路由 -->
        <el-scrollbar class="router">
          <router-view v-slot="{ Component }">
            <keep-alive>
              <component :is="Component" />
            </keep-alive>
          </router-view>
        </el-scrollbar>
      </el-main>
    </el-container>
  </el-container>
</template>

<style scoped lang="scss">
.index {
  background-color: #f5f5f5;
  .header-wrapper {
    height: 50px;
    background-color: #3692eb;
    .header {
      height: 100%;
      border-bottom: unset;
      display: flex;
      color: white;
      .logo {
        display: flex;
        align-items: center;
        font-size: 18px;
        .separator {
          width: 1px;
          height: 22px;
          background-color: #ffffff;
          margin: 0 10px;
        }
      }
      .flex-grow {
        flex-grow: 1;
      }
      .username-wrapper {
        display: flex;
        align-items: center;
        font-size: 12px;

        .username {
          color: white;
          margin-left: 5px;
        }
        .avatar {
          margin-left: 10px;
        }
        .dropdown {
          margin-left: 10px;
          color: white;
        }
      }
    }
  }
  .menu-router {
    .aside-menu-wrapper {
      border-right: solid 1px var(--el-menu-border-color);
      box-shadow: 1px 20px 10px #e3e3e3;
      padding: 0 20px;
      background-color: white;
      height: calc(100vh - 50px);
      flex-shrink: 0;
      overflow: scroll;
    }
    .router-wrapper {
      border-top: var(--el-border-color-light) 1px solid;
      padding: 0;
      height: calc(100vh - 50px);

      .router-header {
        padding: 0;
        height: 40px;
        background-color: white;
        display: flex;
        align-items: center;

        .fold-wrapper {
          height: 100%;
          display: flex;
          align-items: center;

          .fold {
            &:hover {
              cursor: pointer;
            }

            color: var(--el-color-primary);
            margin-left: 10px;
            transform: rotate(0deg);
            transition: transform 0.3s linear;
          }

          .expand {
            transform: rotate(180deg);
          }
        }
      }
      .router {
        height: calc(100vh - 130px);
        margin: 20px 20px 0px 20px;
        border-radius: 5px;

        :deep(.el-scrollbar__view) {
          height: 100%;
        }
      }
    }
  }
}
</style>
