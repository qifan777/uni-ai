import { createRouter, createWebHashHistory, createWebHistory } from 'vue-router'
import RegisterView from '@/views/login/register-view.vue'
import LoginView from '@/views/login/login-view.vue'
import DictView from '@/views/dict/dict-view.vue'
import RoleView from '@/views/role/role-view.vue'
import MenuView from '@/views/menu/menu-view.vue'
import LayoutView from '@/layout/layout-view.vue'
import { useHomeStore } from '@/stores/home-store'
import RestPasswordView from '@/views/login/rest-password-view.vue'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'layout-view',
      component: LayoutView,
      children: [
        { path: '/user', component: () => import('@/views/user/user-view.vue') },
        {
          path: '/role',
          name: 'role',
          component: RoleView
        },
        {
          path: '/menu',
          name: 'menu',
          component: MenuView
        },
        {
          path: '/dict',
          name: 'dict',
          component: DictView
        },
        { path: '/wallet', component: () => import('@/views/wallet/wallet/wallet-view.vue') },
        {
          path: '/wallet-item',
          component: () => import('@/views/wallet/wallet-item/wallet-item-view.vue')
        },
        {
          path: '/wallet-order',
          component: () => import('@/views/wallet/wallet-order/wallet-order-view.vue')
        },
        {
          path: '/wallet-record',
          component: () => import('@/views/wallet/wallet-record/wallet-record-view.vue')
        },
        {
          path: '/wallet-record-stats',
          component: () => import('@/views/wallet/wallet-record/wallet-record-stats.vue')
        },
        {
          path: '/ai-message',
          component: () => import('@/views/ai/ai-message/ai-message-view.vue')
        },
        {
          path: '/ai-session',
          component: () => import('@/views/ai/ai-session/ai-session-view.vue')
        },
        {
          path: '/ai-role',
          component: () => import('@/views/ai/ai-role/ai-role-view.vue')
        },
        {
          path: '/ai-factory',
          component: () => import('@/views/ai/ai-factory/ai-factory-view.vue')
        },
        {
          path: '/ai-tag',
          component: () => import('@/views/ai/ai-tag/ai-tag-view.vue')
        },
        {
          path: '/ai-model',
          component: () => import('@/views/ai/ai-model/ai-model-view.vue')
        },
        {
          path: '/ai-document',
          component: () => import('@/views/ai/ai-document/ai-document-view.vue')
        },
        {
          path: '/ai-collection',
          component: () => import('@/views/ai/ai-collection/ai-collection-view.vue')
        },
        {
          path: '/setting',
          component: () => import('@/views/setting/setting-view.vue')
        }
      ]
    },
    {
      path: '/chat',
      component: () => import('@/views/ai/ai-chat/ai-chat-view.vue')
    },
    {
      path: '/login',
      name: 'login',
      component: LoginView
    },
    {
      path: '/register',
      name: 'register',
      component: RegisterView
    },
    {
      path: '/rest-password',
      component: RestPasswordView
    }
  ]
})
// 添加路由拦截，在进入路由之前需要校验是否有该菜单的权限
// eslint-disable-next-line no-sparse-arrays
const whiteList = ['/login', '/register', '/rest-password', '/']
router.beforeEach(async (to, from, next) => {
  const homeStore = useHomeStore()
  if (
    whiteList.includes(to.path) ||
    (await homeStore.getMenuList()).findIndex((menu) => menu.path === to.path) >= 0
  ) {
    next()
  } else {
    return next('/')
  }
})
export default router
