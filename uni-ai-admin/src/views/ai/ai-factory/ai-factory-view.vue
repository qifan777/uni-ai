<script lang="ts" setup>
import AiFactoryTable from './components/ai-factory-table.vue'
import AiFactoryQuery from './components/ai-factory-query.vue'
import AiFactoryDialog from './components/ai-factory-dialog.vue'
</script>
<template>
  <div class="ai-factory-view">
    <ai-factory-query></ai-factory-query>
    <ai-factory-dialog></ai-factory-dialog>
    <ai-factory-table></ai-factory-table>
  </div>
</template>

<style lang="scss" scoped>
.ai-factory-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
