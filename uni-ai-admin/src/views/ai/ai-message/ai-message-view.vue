<script lang="ts" setup>
import AiMessageTable from './components/ai-message-table.vue'
import AiMessageQuery from './components/ai-message-query.vue'
import AiMessageDialog from './components/ai-message-dialog.vue'
</script>
<template>
  <div class="ai-message-view">
    <ai-message-query></ai-message-query>
    <ai-message-dialog></ai-message-dialog>
    <ai-message-table></ai-message-table>
  </div>
</template>

<style lang="scss" scoped>
.ai-message-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
