<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useDictStore } from '../store/dict-store'
import DictCreateForm from './dict-create-form.vue'
import DictUpdateForm from './dict-update-form.vue'
import type { EditMode } from '@/typings'

const dictStore = useDictStore()
const { dialogData } = storeToRefs(dictStore)

const formMap: Record<EditMode, Component> = {
  CREATE: DictCreateForm,
  UPDATE: DictUpdateForm
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
