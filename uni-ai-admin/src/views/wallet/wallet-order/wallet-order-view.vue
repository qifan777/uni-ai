<script lang="ts" setup>
import WalletOrderTable from './components/wallet-order-table.vue'
import WalletOrderQuery from './components/wallet-order-query.vue'
import WalletOrderDialog from './components/wallet-order-dialog.vue'
</script>
<template>
  <div class="wallet-order-view">
    <wallet-order-query></wallet-order-query>
    <wallet-order-dialog></wallet-order-dialog>
    <wallet-order-table></wallet-order-table>
  </div>
</template>

<style lang="scss" scoped>
.wallet-order-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
