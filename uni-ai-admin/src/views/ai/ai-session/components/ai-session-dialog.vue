<script lang="ts" setup>
import {type Component} from 'vue'
import {storeToRefs} from 'pinia'
import {useAiSessionStore} from '../store/ai-session-store'
import AiSessionCreateForm from './ai-session-create-form.vue'
import AiSessionUpdateForm from './ai-session-update-form.vue'
import type {EditMode} from '@/typings'

const aiSessionStore = useAiSessionStore()
const { dialogData } = storeToRefs(aiSessionStore)

const formMap: Record<EditMode, Component> = {
  CREATE: AiSessionCreateForm,
  UPDATE: AiSessionUpdateForm
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
