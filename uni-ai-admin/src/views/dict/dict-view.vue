<script lang="ts" setup>
import DictTable from './components/dict-table.vue'
import DictQuery from './components/dict-query.vue'
import DictDialog from './components/dict-dialog.vue'
</script>
<template>
  <div class="dict-view">
    <dict-query></dict-query>
    <dict-dialog></dict-dialog>
    <dict-table></dict-table>
  </div>
</template>

<style lang="scss" scoped>
.dict-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
