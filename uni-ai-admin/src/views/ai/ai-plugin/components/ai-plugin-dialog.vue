<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useAiPluginStore } from '../store/ai-plugin-store'
import AiPluginCreateForm from './ai-plugin-create-form.vue'
import AiPluginUpdateForm from './ai-plugin-update-form.vue'
import type { EditMode } from '@/typings'

const aiPluginStore = useAiPluginStore()
const { dialogData } = storeToRefs(aiPluginStore)

const formMap: Record<EditMode, Component> = {
  CREATE: AiPluginCreateForm,
  UPDATE: AiPluginUpdateForm
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
