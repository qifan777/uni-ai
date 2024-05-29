<script lang="ts" setup>
import UserTable from './components/user-table.vue'
import UserQuery from './components/user-query.vue'
import UserDialog from './components/user-dialog.vue'
</script>
<template>
  <div class="user-view">
    <user-query></user-query>
    <user-dialog></user-dialog>
    <user-table></user-table>
  </div>
</template>

<style lang="scss" scoped>
.user-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
