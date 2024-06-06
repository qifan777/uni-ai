<script lang="ts" setup>
import { type Component } from 'vue'
import { storeToRefs } from 'pinia'
import { useWalletRecordStore } from '../store/wallet-record-store'
import WalletRecordCreateForm from './wallet-record-create-form.vue'
import WalletRecordUpdateForm from './wallet-record-update-form.vue'
import type { EditMode } from '@/typings'
const walletRecordStore = useWalletRecordStore()
const { dialogData } = storeToRefs(walletRecordStore)

const formMap: Record<EditMode, Component> = {
  CREATE: WalletRecordCreateForm,
  UPDATE: WalletRecordUpdateForm
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
