<script lang="ts" setup>
import WalletItemTable from './components/wallet-item-table.vue'
import WalletItemQuery from './components/wallet-item-query.vue'
import WalletItemDialog from './components/wallet-item-dialog.vue'
</script>
<template>
  <div class="wallet-item-view">
    <wallet-item-query></wallet-item-query>
    <wallet-item-dialog></wallet-item-dialog>
    <wallet-item-table></wallet-item-table>
  </div>
</template>

<style lang="scss" scoped>
.wallet-item-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
