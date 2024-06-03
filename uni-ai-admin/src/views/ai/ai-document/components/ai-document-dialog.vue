<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useAiDocumentStore } from '../store/ai-document-store'
import AiDocumentCreateForm from './ai-document-create-form.vue'
import AiDocumentUpdateForm from './ai-document-update-form.vue'
import type { EditMode } from '@/typings'

const aiDocumentStore = useAiDocumentStore()
const { dialogData } = storeToRefs(aiDocumentStore)

const formMap: Record<EditMode, Component> = {
  CREATE: AiDocumentCreateForm,
  UPDATE: AiDocumentUpdateForm
}
</script>
<template>
  <div>
    <el-dialog
      fullscreen
      v-model="dialogData.visible"
      :title="dialogData.title"
      :width="dialogData.width"
    >
      <component :is="formMap[dialogData.mode]"></component>
    </el-dialog>
  </div>
</template>

<style lang="scss" scoped></style>
