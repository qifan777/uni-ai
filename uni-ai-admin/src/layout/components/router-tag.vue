<template>
  <div>
    <!-- 右键页签时弹出操作项 -->
    <el-popover
      :key="tag.path"
      :width="100"
      :show-after="100"
      :popper-style="{ padding: 0 }"
      v-model:visible="visible"
      trigger="contextmenu"
    >
      <div>
        <div class="menu-list">
          <div class="menu-item" @click="handleCommand('copy')" v-if="isActive">复制路径</div>
          <div class="menu-item" @click="handleCommand('closeOther')">关闭其他</div>
          <div class="menu-item" @click="handleCommand('closeAll')">关闭所有</div>
        </div>
      </div>
      <template #reference>
        <div :class="['tag', isActive ? 'active' : '', 'tag' + index]" @click="handleTagClick(tag)">
          <div class="rag-link">
            {{ tag.name }}
          </div>
          <!-- 父亲元素也有@click，子元素触发点击事件时要阻止事件继续向外传播。@click.stop -->
          <el-icon class="close" size="14" @click.stop="closeTag(index)">
            <close></close>
          </el-icon>
        </div>
      </template>
    </el-popover>
  </div>
</template>

<script setup lang="ts">
import { type TagMenu, useTagStore } from '@/layout/store/tag-store'
import { storeToRefs } from 'pinia'
import { Close } from '@element-plus/icons-vue'
import { computed, ref } from 'vue'
const props = defineProps<{ tag: TagMenu; index: number }>()
const visible = ref(false)
const isActive = computed(() => {
  return props.tag.path === activeTag.value.path
})
const tagStore = useTagStore()
const { closeAll, closeOther, closeTag, openTag } = tagStore
const { activeTag } = storeToRefs(tagStore)
// 将命令和对应的操作用map映射
const handleCommand = (command: 'copy' | 'closeAll' | 'closeOther') => {
  const commandMap = {
    copy: () => {
      navigator.clipboard.writeText(window.location.href)
    },
    closeAll,
    closeOther: () => {
      closeOther(props.tag)
    }
  }
  commandMap[command]()
  visible.value = false
}

const handleTagClick = (tag: TagMenu) => {
  // tag.route中详细记录了跳转的路由以及参数。当重新打开页签时要保证参数不能丢失
  openTag(tag.route)
}
</script>

<style lang="scss" scoped>
.menu-list {
  display: flex;
  flex-direction: column;
  .menu-item {
    padding: 5px 10px;
    &:hover {
      color: var(--el-color-primary-dark-2);
      background-color: var(--el-color-primary-light-8);
      cursor: pointer;
    }
  }
}
.tag {
  display: flex;
  align-items: center;
  height: 40px;
  line-height: 40px;
  padding: 0 15px;
  justify-content: center;
  border-right: rgba(#d4d7de, 0.5) 1px solid;
  background-color: rgba(#f5f5f5, 0.4);
  position: relative;
  min-width: 140px;

  &:hover {
    cursor: pointer;
  }
  &.active {
    background-color: white;
    border-right: #d4d7de 1px solid;
    .rag-link {
      color: #3692eb;
      font-weight: 500;
    }
    .close {
      width: 14px;
      color: #3692eb;
    }
  }
  &:hover {
    .close {
      width: 14px;
    }
  }
  .close {
    width: 0;
    transition: all 0.3s ease-in-out;
    color: #d4d7de;
  }
  .rag-link {
    transition: all 0.5s ease-in-out;
    margin-right: 5px;
    font-size: 14px;
    // 文字只能显示一行
    -webkit-line-clamp: 1;
    -webkit-box-orient: vertical;
    display: -webkit-box;
    overflow: hidden;
    // 溢出部分用 ...代替
    text-overflow: ellipsis;
  }
}
</style>
