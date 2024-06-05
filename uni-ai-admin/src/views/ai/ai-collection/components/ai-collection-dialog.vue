<script lang="ts" setup>
import {type Component} from 'vue'
import {storeToRefs} from 'pinia'
import {useAiCollectionStore} from '../store/ai-collection-store'
import AiCollectionCreateForm from './ai-collection-create-form.vue'
import AiCollectionUpdateForm from './ai-collection-update-form.vue'
import type {EditMode} from '@/typings'

const aiCollectionStore = useAiCollectionStore()
const { dialogData } = storeToRefs(aiCollectionStore)

const formMap: Record<EditMode, Component> = {
  CREATE: AiCollectionCreateForm,
  UPDATE: AiCollectionUpdateForm
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
