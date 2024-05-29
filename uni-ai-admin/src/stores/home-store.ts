import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { MenuDto, UserDto } from '@/apis/__generated/model/dto'
import { api } from '@/utils/api-instance'
import type { MenuTreeDto } from '@/typings'
import { buildMenuTree } from '@/views/menu/store/menu-store'
import { useTagStore } from '@/layout/store/tag-store'

export const useHomeStore = defineStore('home', () => {
  const userInfo = ref<UserDto['UserRepository/COMPLEX_FETCHER_FOR_ADMIN']>()
  const menuList = ref<MenuDto['MenuRepository/SIMPLE_FETCHER'][]>([])
  const menuTreeList = ref<MenuTreeDto[]>([])
  const getUserInfo = async () => {
    userInfo.value = await api.userForFrontController.getUserInfo()
    return userInfo.value
  }
  const getMenuList = async () => {
    if (menuList.value.length > 0) return menuList.value
    // 获取用户菜单
    const res = await api.userForFrontController.getUserMenus()
    // 缓存菜单列表
    menuList.value = res
    // 递归生成菜单树
    menuTreeList.value = buildMenuTree(null, res)
    return res
  }
  const init = async () => {
    await getUserInfo()
    await getMenuList()
  }
  const logout = () => {
    useTagStore().tags = []
    menuTreeList.value = []
    menuList.value = []
    userInfo.value = undefined
  }
  return { userInfo, getUserInfo, init, getMenuList, logout, menuList, menuTreeList }
})
