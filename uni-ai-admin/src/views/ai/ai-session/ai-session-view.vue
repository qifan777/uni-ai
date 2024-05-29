<script lang="ts" setup>
import AiSessionTable from './components/ai-session-table.vue'
import AiSessionQuery from './components/ai-session-query.vue'
import AiSessionDialog from './components/ai-session-dialog.vue'
</script>
<template>
  <div class="ai-session-view">
    <ai-session-query></ai-session-query>
    <ai-session-dialog></ai-session-dialog>
    <ai-session-table></ai-session-table>
  </div>
</template>

<style lang="scss" scoped>
.ai-session-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
