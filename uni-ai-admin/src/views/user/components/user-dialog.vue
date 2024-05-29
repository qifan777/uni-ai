<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useUserStore } from '../store/user-store'
import UserCreateForm from './user-create-form.vue'
import UserUpdateForm from './user-update-form.vue'
import type { EditMode } from '@/typings'

const userStore = useUserStore()
const { dialogData } = storeToRefs(userStore)

const formMap: Record<EditMode, Component> = {
  CREATE: UserCreateForm,
  UPDATE: UserUpdateForm
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
