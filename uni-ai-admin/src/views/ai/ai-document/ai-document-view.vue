<script lang="ts" setup>
import AiDocumentTable from './components/ai-document-table.vue'
import AiDocumentQuery from './components/ai-document-query.vue'
import AiDocumentDialog from './components/ai-document-dialog.vue'
</script>
<template>
  <div class="ai-document-view">
    <ai-document-query></ai-document-query>
    <ai-document-dialog></ai-document-dialog>
    <ai-document-table></ai-document-table>
  </div>
</template>

<style lang="scss" scoped>
.ai-document-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
