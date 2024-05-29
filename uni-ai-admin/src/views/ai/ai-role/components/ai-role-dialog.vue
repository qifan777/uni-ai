<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useAiRoleStore } from '../store/ai-role-store'
import AiRoleCreateForm from './ai-role-create-form.vue'
import AiRoleUpdateForm from './ai-role-update-form.vue'
import type { EditMode } from '@/typings'

const aiRoleStore = useAiRoleStore()
const { dialogData } = storeToRefs(aiRoleStore)

const formMap: Record<EditMode, Component> = {
  CREATE: AiRoleCreateForm,
  UPDATE: AiRoleUpdateForm
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
