<script lang="ts" setup>
import AiTagTable from './components/ai-tag-table.vue'
import AiTagQuery from './components/ai-tag-query.vue'
import AiTagDialog from './components/ai-tag-dialog.vue'
</script>
<template>
  <div class="ai-tag-view">
    <ai-tag-query></ai-tag-query>
    <ai-tag-dialog></ai-tag-dialog>
    <ai-tag-table></ai-tag-table>
  </div>
</template>

<style lang="scss" scoped>
.ai-tag-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
