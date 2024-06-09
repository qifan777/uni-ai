<script lang="ts" setup>
import { toRefs } from 'vue'
import { useAiPluginStore } from '../store/ai-plugin-store'
import { storeToRefs } from 'pinia'

const aiPluginStore = useAiPluginStore()
const { queryData } = storeToRefs(aiPluginStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="函数名称">
        <el-input v-model="query.name"></el-input>
      </el-form-item>
      <el-form-item label="函数描述">
        <el-input v-model="query.description"></el-input>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="aiPluginStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="aiPluginStore.restQuery"> 重置</el-button>
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
