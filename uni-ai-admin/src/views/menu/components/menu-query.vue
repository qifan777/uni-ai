<script lang="ts" setup>
import { toRefs } from 'vue'
import { useMenuStore } from '../store/menu-store'
import { storeToRefs } from 'pinia'
import DictSelect from '@/components/dict/dict-select.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'

const menuStore = useMenuStore()
const { queryData } = storeToRefs(menuStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="菜单名称">
        <el-input v-model="query.name"></el-input>
      </el-form-item>
      <el-form-item label="父菜单Id">
        <el-input v-model="query.parentId"></el-input>
      </el-form-item>
      <el-form-item label="路由路径">
        <el-input v-model="query.path"></el-input>
      </el-form-item>
      <el-form-item label="排序号">
        <el-input-number v-model="query.orderNum" controls-position="right"></el-input-number>
      </el-form-item>
      <el-form-item label="菜单类型">
        <dict-select :dict-id="DictConstants.MENU_TYPE" v-model="query.menuType"></dict-select>
      </el-form-item>
      <el-form-item label="是否可见">
        <el-switch v-model="query.visible"></el-switch>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="menuStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="menuStore.restQuery()"> 重置</el-button>
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
