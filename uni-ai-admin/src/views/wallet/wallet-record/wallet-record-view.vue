<script lang="ts" setup>
    import WalletRecordTable from './components/wallet-record-table.vue'
    import WalletRecordQuery from './components/wallet-record-query.vue'
    import WalletRecordDialog from './components/wallet-record-dialog.vue'
</script>
<template>
    <div class="wallet-record-view">
        <wallet-record-query></wallet-record-query>
        <wallet-record-dialog></wallet-record-dialog>
        <wallet-record-table></wallet-record-table>
    </div>
</template>

<style lang="scss" scoped>
.wallet-record-view {
    background: white;
    padding: 20px;
    border-radius: 5px;
}
</style>
