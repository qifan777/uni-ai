<script lang="ts" setup>
import {type Component} from 'vue'
import {storeToRefs} from 'pinia'
import {useAiFactoryStore} from '../store/ai-factory-store'
import AiFactoryCreateForm from './ai-factory-create-form.vue'
import AiFactoryUpdateForm from './ai-factory-update-form.vue'
import type {EditMode} from '@/typings'

const aiFactoryStore = useAiFactoryStore()
const { dialogData } = storeToRefs(aiFactoryStore)

const formMap: Record<EditMode, Component> = {
  CREATE: AiFactoryCreateForm,
  UPDATE: AiFactoryUpdateForm
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
