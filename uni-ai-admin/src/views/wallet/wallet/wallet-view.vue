<script lang="ts" setup>
import WalletTable from './components/wallet-table.vue'
import WalletQuery from './components/wallet-query.vue'
import WalletDialog from './components/wallet-dialog.vue'
</script>
<template>
  <div class="wallet-view">
    <wallet-query></wallet-query>
    <wallet-dialog></wallet-dialog>
    <wallet-table></wallet-table>
  </div>
</template>

<style lang="scss" scoped>
.wallet-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
