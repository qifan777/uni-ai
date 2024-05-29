<script lang="ts" setup>
import { onMounted, toRefs } from 'vue'
import { api } from '@/utils/api-instance'
import type { Scope } from '@/typings'
import type { UserDto } from '@/apis/__generated/model/dto'
import DictColumn from '@/components/dict/dict-column.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type { UserSpec } from '@/apis/__generated/model/static'
import DictSelect from '@/components/dict/dict-select.vue'

type UserScope = Scope<UserDto['UserRepository/COMPLEX_FETCHER_FOR_ADMIN']>
const initQuery: UserSpec = {}
const {
  reloadTableData,
  loading,
  pageData,
  handleSelectChange,
  handleSortChange,
  loadTableData,
  queryRequest,
  getTableSelectedRows
} = useTableHelper(api.userForAdminController.query, api.userForAdminController, initQuery)
const { queryData, restQuery } = useQueryHelper(initQuery)
const { query } = toRefs(queryData.value)
onMounted(() => {
  reloadTableData()
})

defineExpose({ getTableSelectedRows })
</script>
<template>
  <div>
    <el-form inline label-width="80" size="small">
      <el-form-item label="手机号">
        <el-input v-model="query.phone"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="query.nickname"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <dict-select v-model="query.gender" :dict-id="1001"></dict-select>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            size="small"
            type="primary"
            @click="reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button size="small" type="warning" @click="restQuery"> 重置</el-button>
        </div>
      </el-form-item>
    </el-form>
    <el-table
      ref="table"
      v-loading="loading"
      :border="true"
      :data="pageData.content"
      @selection-change="handleSelectChange"
      @sort-change="handleSortChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="手机号" prop="phone" sortable="custom">
        <template v-slot:default="{ row }: UserScope">
          {{ row.phone }}
        </template>
      </el-table-column>
      <el-table-column label="昵称" prop="nickname" sortable="custom">
        <template v-slot:default="{ row }: UserScope">
          {{ row.nickname }}
        </template>
      </el-table-column>
      <el-table-column label="头像" prop="avatar" sortable="custom">
        <template v-slot:default="{ row }: UserScope">
          <el-avatar :src="row.avatar" alt=""></el-avatar>
        </template>
      </el-table-column>
      <el-table-column label="性别" prop="gender" sortable="custom">
        <template v-slot:default="{ row }: UserScope">
          <dict-column :dict-id="1001" :value="row.gender"></dict-column>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createdTime" sortable="custom">
        <template v-slot:default="{ row }: UserScope">
          {{ row.createdTime }}
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="editedTime" sortable="custom">
        <template v-slot:default="{ row }: UserScope">
          {{ row.editedTime }}
        </template>
      </el-table-column>
    </el-table>
    <div class="page">
      <el-pagination
        :current-page="queryRequest.pageNum"
        :page-size="queryRequest.pageSize"
        :page-sizes="[10, 20, 30, 40, 50]"
        :total="pageData.totalElements"
        background
        layout="prev, pager, next, jumper, total, sizes"
        small
        style="margin-top: 30px"
        @current-change="(pageNum) => loadTableData({ pageNum })"
        @size-change="(pageSize) => loadTableData({ pageSize })"
      />
    </div>
  </div>
</template>

<style lang="scss" scoped>
.button-section {
  margin: 20px 0;
}

.page {
  display: flex;
  justify-content: flex-end;
}
</style>
