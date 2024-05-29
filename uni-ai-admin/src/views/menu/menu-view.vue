<script lang="ts" setup>
import MenuTable from './components/menu-table.vue'
import MenuQuery from './components/menu-query.vue'
import MenuDialog from './components/menu-dialog.vue'
</script>
<template>
  <div class="menu-view">
    <menu-query></menu-query>
    <menu-dialog></menu-dialog>
    <menu-table></menu-table>
  </div>
</template>

<style lang="scss" scoped>
.menu-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
