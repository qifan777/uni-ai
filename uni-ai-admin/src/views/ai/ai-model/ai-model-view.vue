<script lang="ts" setup>
import AiModelTable from './components/ai-model-table.vue'
import AiModelQuery from './components/ai-model-query.vue'
import AiModelDialog from './components/ai-model-dialog.vue'
</script>
<template>
  <div class="ai-model-view">
    <ai-model-query></ai-model-query>
    <ai-model-dialog></ai-model-dialog>
    <ai-model-table></ai-model-table>
  </div>
</template>

<style lang="scss" scoped>
.ai-model-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
