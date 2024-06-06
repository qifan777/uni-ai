<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useAiTagStore } from '../store/ai-tag-store'
import AiTagCreateForm from './ai-tag-create-form.vue'
import AiTagUpdateForm from './ai-tag-update-form.vue'
import type { EditMode } from '@/typings'

const aiTagStore = useAiTagStore()
const { dialogData } = storeToRefs(aiTagStore)

const formMap: Record<EditMode, Component> = {
  CREATE: AiTagCreateForm,
  UPDATE: AiTagUpdateForm
}
</script>
<template>
  <div>
    <el-dialog v-model="dialogData.visible" :title="dialogData.title" :width="dialogData.width">
      <component :is="formMap[dialogData.mode]"></component>
    </el-dialog>
  </div>
</template>

<style lang="scss" scoped></style>
