<script setup lang="ts">
import { computed } from 'vue'
import type { AiMessage } from '@/views/ai/ai-chat/store/ai-chat-store'

const props = defineProps<{ message: AiMessage }>()
const images = computed(() => {
  return props.message.content
    .map((item) => {
      if (item['image']) {
        return item['image']
      }
      return ''
    })
    .filter((item) => item)
})
const text = computed(() => {
  const texts = props.message.content
    .map((item) => {
      if (item['text']) {
        return item['text']
      }
      return ''
    })
    .filter((item) => item)
  return texts.length > 0 ? texts[0] : ''
})
</script>

<template>
  <el-image
    class="image"
    fit="cover"
    style="width: 300px; height: 300px"
    v-for="image in images"
    :preview-src-list="images"
    :src="image"
  ></el-image>
  <MdPreview
    v-if="text"
    :id="'preview-only'"
    :model-value="text"
    :preview-theme="'smart-blue'"
    :style="{
      backgroundColor: message.type === 'USER' ? 'rgb(231, 248, 255)' : '#f4f4f5'
    }"
  ></MdPreview>
</template>

<style scoped lang="scss">
// 调整markdown组件的一些样式，deep可以修改组件内的样式，正常情况是scoped只能修改本组件的样式。
:deep(.md-editor-preview-wrapper) {
  padding: 0 10px;

  .smart-blue-theme p {
    line-height: unset;
  }
}
</style>
