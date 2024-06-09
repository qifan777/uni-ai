<script lang="ts" setup>
import AiPluginTable from './components/ai-plugin-table.vue'
import AiPluginQuery from './components/ai-plugin-query.vue'
import AiPluginDialog from './components/ai-plugin-dialog.vue'
</script>
<template>
  <div class="ai-plugin-view">
    <ai-plugin-query></ai-plugin-query>
    <ai-plugin-dialog></ai-plugin-dialog>
    <ai-plugin-table></ai-plugin-table>
  </div>
</template>

<style lang="scss" scoped>
.ai-plugin-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
