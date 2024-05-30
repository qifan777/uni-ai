<script lang="ts" setup>
import AiCollectionTable from './components/ai-collection-table.vue'
import AiCollectionQuery from './components/ai-collection-query.vue'
import AiCollectionDialog from './components/ai-collection-dialog.vue'
</script>
<template>
  <div class="ai-collection-view">
    <ai-collection-query></ai-collection-query>
    <ai-collection-dialog></ai-collection-dialog>
    <ai-collection-table></ai-collection-table>
  </div>
</template>

<style lang="scss" scoped>
.ai-collection-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
