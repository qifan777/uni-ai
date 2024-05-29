<script lang="ts" setup>
import AiRoleTable from './components/ai-role-table.vue'
import AiRoleQuery from './components/ai-role-query.vue'
import AiRoleDialog from './components/ai-role-dialog.vue'
</script>
<template>
  <div class="ai-role-view">
    <ai-role-query></ai-role-query>
    <ai-role-dialog></ai-role-dialog>
    <ai-role-table></ai-role-table>
  </div>
</template>

<style lang="scss" scoped>
.ai-role-view {
  background: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
