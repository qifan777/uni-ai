<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useRoleStore } from '../store/role-store'
import RoleCreateForm from './role-create-form.vue'
import RoleUpdateForm from './role-update-form.vue'
import type { EditMode } from '@/typings'

const roleStore = useRoleStore()
const { dialogData } = storeToRefs(roleStore)

const formMap: Record<EditMode, Component> = {
  CREATE: RoleCreateForm,
  UPDATE: RoleUpdateForm
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
