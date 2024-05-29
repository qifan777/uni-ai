<script lang="ts" setup>
import RoleTable from './components/role-table.vue'
import RoleQuery from './components/role-query.vue'
import RoleDialog from './components/role-dialog.vue'
</script>
<template>
  <div class="role-view">
    <role-query></role-query>
    <role-dialog></role-dialog>
    <role-table></role-table>
  </div>
</template>

<style lang="scss" scoped>
.role-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
