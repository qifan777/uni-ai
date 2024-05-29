<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useWalletItemStore } from '../store/wallet-item-store'
import WalletItemCreateForm from './wallet-item-create-form.vue'
import WalletItemUpdateForm from './wallet-item-update-form.vue'
import type { EditMode } from '@/typings'
const walletItemStore = useWalletItemStore()
const { dialogData } = storeToRefs(walletItemStore)

const formMap: Record<EditMode, Component> = {
  CREATE: WalletItemCreateForm,
  UPDATE: WalletItemUpdateForm
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
