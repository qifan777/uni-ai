<script lang="ts" setup>
import { toRefs } from 'vue'
import { useAiTagStore } from '../store/ai-tag-store'
import { storeToRefs } from 'pinia'
import DictSelect from '@/components/dict/dict-select.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'

const aiTagStore = useAiTagStore()
const { queryData } = storeToRefs(aiTagStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="标签">
        <dict-select :dict-id="DictConstants.AI_MODEL_TAG" v-model="query.name"></dict-select>
      </el-form-item>
      <el-form-item label="厂家">
        <dict-select :dict-id="DictConstants.AI_FACTORY_TYPE" v-model="query.factory"></dict-select>
      </el-form-item>
      <el-form-item label="SpringAIModel">
        <el-input v-model="query.springAiModel"></el-input>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="aiTagStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="aiTagStore.restQuery"> 重置</el-button>
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
