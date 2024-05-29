<script lang="ts" setup>
import { toRefs } from 'vue'
import { useAiRoleStore } from '../store/ai-role-store'
import { storeToRefs } from 'pinia'
import { aiModelQueryOptions } from '@/views/ai/ai-model/store/ai-model-store'
import RemoteSelect from '@/components/base/form/remote-select.vue'

const aiRoleStore = useAiRoleStore()
const { queryData } = storeToRefs(aiRoleStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="角色名称">
        <el-input v-model="query.name"></el-input>
      </el-form-item>
      <el-form-item label="模型">
        <remote-select
          label-prop="name"
          :query-options="aiModelQueryOptions"
          v-model="query.aiModelId"
        ></remote-select>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="aiRoleStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="aiRoleStore.restQuery()"> 重置</el-button>
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
