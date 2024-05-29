<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useWalletStore } from '../store/wallet-store'
import WalletCreateForm from './wallet-create-form.vue'
import WalletUpdateForm from './wallet-update-form.vue'
import type { EditMode } from '@/typings'
const walletStore = useWalletStore()
const { dialogData } = storeToRefs(walletStore)

const formMap: Record<EditMode, Component> = {
  CREATE: WalletCreateForm,
  UPDATE: WalletUpdateForm
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
