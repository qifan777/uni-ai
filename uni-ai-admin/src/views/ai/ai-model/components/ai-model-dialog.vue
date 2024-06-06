<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useAiModelStore } from '../store/ai-model-store'
import AiModelCreateForm from './ai-model-create-form.vue'
import AiModelUpdateForm from './ai-model-update-form.vue'
import type { EditMode } from '@/typings'

const aiModelStore = useAiModelStore()
const { dialogData } = storeToRefs(aiModelStore)

const formMap: Record<EditMode, Component> = {
  CREATE: AiModelCreateForm,
  UPDATE: AiModelUpdateForm
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
