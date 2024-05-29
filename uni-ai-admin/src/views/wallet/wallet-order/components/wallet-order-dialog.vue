<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useWalletOrderStore } from '../store/wallet-order-store'
import WalletOrderCreateForm from './wallet-order-create-form.vue'
import WalletOrderUpdateForm from './wallet-order-update-form.vue'
import type { EditMode } from '@/typings'
const walletOrderStore = useWalletOrderStore()
const { dialogData } = storeToRefs(walletOrderStore)

const formMap: Record<EditMode, Component> = {
  CREATE: WalletOrderCreateForm,
  UPDATE: WalletOrderUpdateForm
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
