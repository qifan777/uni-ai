<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useAiMessageStore } from '../store/ai-message-store'
import AiMessageCreateForm from './ai-message-create-form.vue'
import AiMessageUpdateForm from './ai-message-update-form.vue'
import type { EditMode } from '@/typings'

const aiMessageStore = useAiMessageStore()
const { dialogData } = storeToRefs(aiMessageStore)

const formMap: Record<EditMode, Component> = {
  CREATE: AiMessageCreateForm,
  UPDATE: AiMessageUpdateForm
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
