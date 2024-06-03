import router from '@/router'
import { defineStore, storeToRefs } from 'pinia'
import type { RouteLocationPathRaw } from 'vue-router'
import type { MenuDto } from '@/apis/__generated/model/dto'
import { useHomeStore } from '@/stores/home-store'
import { useStorage } from '@vueuse/core'
import { ref } from 'vue'
// tag是基于menu拓展的，不需要太多的不必要属性，从menu中选择（id，name，menuType，icon，path）再加上（reloadKey和route（跳转路由））
export type TagMenu = Pick<
  MenuDto['MenuRepository/COMPLEX_FETCHER'],
  'id' | 'name' | 'menuType' | 'icon' | 'path'
> & {
  reloadKey: number
  route: RouteLocationPathRaw
}
export const useTagStore = defineStore('tags', () => {
  const { menuList } = storeToRefs(useHomeStore())
  // 当前激活的页签
  const activeTag = useStorage<TagMenu>('activeTag', {
    id: '',
    menuType: 'DIRECTORY',
    name: '',
    path: '',
    reloadKey: 0,
    route: { path: '' }
  })
  const collapse = ref(false)
  // 页签列表
  const tags = useStorage<TagMenu[]>('tagList', [])
  // 打开页签的时候可以传入"/user"或者{path: "/user",query:{id:1}}这两种类型。和使用router.push差不多。
  const openTag = async (path: string | RouteLocationPathRaw) => {
    // 如果path是 string把path变成 {path: path}格式，统一参数。
    const route = typeof path == 'string' ? { path } : path
    // 根据path在用户的菜单列表中查找菜单
    const menu = menuList.value.find((value) => value.path === route.path)
    // 如果菜单类型不是PAGE则返回
    if (!menu || menu.menuType != 'PAGE') return
    const currentIndex = tags.value.findIndex((item) => {
      return item.path === menu.path
    })
    // 设置激活菜单
    activeTag.value = { ...menu, reloadKey: 0, route }
    // 如果页签列表中已经存在菜单，说明之前打开过相同的路径。不存在在新建一个页签，存在替换。
    if (currentIndex === -1) {
      // 新增页签
      tags.value.push(activeTag.value)
    } else {
      // 替换旧的页签为新的页签
      tags.value.splice(currentIndex, 1, activeTag.value)
    }
    await router.push(route)
    return menu
  }
  const closeTag = async (index: number) => {
    const delItem = tags.value[index]
    // 删除页签
    tags.value.splice(index, 1)
    // [1,2,3,4] 如果删除索引2，对应的元素是3。删除后列表变为[1,2,4]，此时索引2对应的元素是4。
    // [1,2,3] 如果删除索引2，删除后的列表变为[1,2]，此时索引2对应的元素为空。
    // [1] 如果删除索引为0，删除后的列表变为[]，此时索引0对应的元素为空。
    // 根据上面的三个例子可以知道，当关闭一个页签时，有三种选择
    // 1. 打开删除后列表的相同索引（原来后面的，选择补位到前面了）
    // 2. 打开删除后列表的前面所有（原来前面的）
    // 3. 删除后列表已经为空了，打开首页。
    const item = tags.value[index] ? tags.value[index] : tags.value[index - 1]
    if (item) {
      delItem.id === activeTag.value.id && (await openTag(item.path))
    } else {
      // 回到首页
      await router.push('/')
    }
  }
  // 清空页签，并回到首页
  const closeAll = async () => {
    tags.value = []
    await router.push('/')
  }
  // 关闭其他页签
  const closeOther = (tag: TagMenu) => {
    tags.value = [tag]
  }
  return {
    activeTag,
    tags,
    collapse,
    closeTag,
    closeAll,
    closeOther,
    openTag
  }
})
