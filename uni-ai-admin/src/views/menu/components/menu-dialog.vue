<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useMenuStore } from '../store/menu-store'
import MenuCreateForm from './menu-create-form.vue'
import MenuUpdateForm from './menu-update-form.vue'
import type { EditMode } from '@/typings'

const menuStore = useMenuStore()
const { dialogData } = storeToRefs(menuStore)

const formMap: Record<EditMode, Component> = {
  CREATE: MenuCreateForm,
  UPDATE: MenuUpdateForm
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
