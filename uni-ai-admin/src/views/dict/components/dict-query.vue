<script lang="ts" setup>
import { toRefs } from 'vue'
import { useDictStore } from '../store/dict-store'
import { storeToRefs } from 'pinia'

const dictStore = useDictStore()
const { queryData } = storeToRefs(dictStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="值编号">
        <el-input-number v-model="query.keyId" controls-position="right"></el-input-number>
      </el-form-item>
      <el-form-item label="值名称">
        <el-input v-model="query.keyName"></el-input>
      </el-form-item>
      <el-form-item label="值英文名称">
        <el-input v-model="query.keyEnName"></el-input>
      </el-form-item>
      <el-form-item label="字典编号">
        <el-input-number v-model="query.dictId" controls-position="right"></el-input-number>
      </el-form-item>
      <el-form-item label="字典名称">
        <el-input v-model="query.dictName"></el-input>
      </el-form-item>
      <el-form-item label="字段英文名称">
        <el-input v-model="query.dictEnName"></el-input>
      </el-form-item>
      <el-form-item label="排序号">
        <el-input-number v-model="query.orderNum" controls-position="right"></el-input-number>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="dictStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="dictStore.restQuery"> 重置</el-button>
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
