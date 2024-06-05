<script lang="ts" setup>
import {toRefs} from 'vue'
import {useAiSessionStore} from '../store/ai-session-store'
import {storeToRefs} from 'pinia'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import {aiRoleQueryOptions} from '@/views/ai/ai-role/store/ai-role-store'

const aiSessionStore = useAiSessionStore()
const { queryData } = storeToRefs(aiSessionStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="会话名称">
        <el-input v-model="query.name"></el-input>
      </el-form-item>
      <el-form-item label="角色">
        <remote-select
          label-prop="name"
          :query-options="aiRoleQueryOptions"
          v-model="query.aiRoleId"
        ></remote-select>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="aiSessionStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="aiSessionStore.restQuery"> 重置</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<style lang="scss" scoped>
:deep(.el-form-item) {
  margin-bottom: 5px;
}

.search {
  display: flex;
  flex-flow: column nowrap;
  width: 100%;

  .btn-wrapper {
    margin-left: 20px;
  }
}
</style>
