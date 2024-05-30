<script lang="ts" setup>
import { toRefs } from 'vue'
import { useAiDocumentStore } from '../store/ai-document-store'
import { storeToRefs } from 'pinia'

const aiDocumentStore = useAiDocumentStore()
const { queryData } = storeToRefs(aiDocumentStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="文档链接">
        <el-input v-model="query.url"></el-input>
      </el-form-item>
      <el-form-item label="集合名称(表名)">
        <el-input v-model="query.collection"></el-input>
      </el-form-item>
      <el-form-item label="总结">
        <el-input v-model="query.summary"></el-input>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="aiDocumentStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="aiDocumentStore.restQuery()">
            重置</el-button
          >
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
