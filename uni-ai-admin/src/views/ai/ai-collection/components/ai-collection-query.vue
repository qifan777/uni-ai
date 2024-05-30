<script lang="ts" setup>
import { toRefs } from 'vue'
import { useAiCollectionStore } from '../store/ai-collection-store'
import { storeToRefs } from 'pinia'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { aiModelQueryOptions } from '@/views/ai/ai-model/store/ai-model-store'

const aiCollectionStore = useAiCollectionStore()
const { queryData } = storeToRefs(aiCollectionStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="名称">
        <el-input v-model="query.name"></el-input>
      </el-form-item>
      <el-form-item label="英文名称" prop="collectionName">
        <el-input v-model="query.collectionName"></el-input>
      </el-form-item>
      <el-form-item label="嵌入模型">
        <remote-select
          label-prop="name"
          :query-options="(query, id) => aiModelQueryOptions(query, id, ['EMBEDDINGS'])"
          v-model="query.embeddingModelId"
        ></remote-select>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="aiCollectionStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="aiCollectionStore.restQuery()">
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
