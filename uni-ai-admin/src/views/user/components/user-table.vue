<script lang="ts" setup>
import { onMounted } from 'vue'
import { storeToRefs } from 'pinia'
import { assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import { ElMessageBox } from 'element-plus'
import type { Scope } from '@/typings'
import { useUserStore } from '../store/user-store'
import type { UserDto } from '@/apis/__generated/model/dto'
import { Delete, Edit, Plus } from '@element-plus/icons-vue'
import DictColumn from '@/components/dict/dict-column.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'

type UserScope = Scope<UserDto['UserRepository/COMPLEX_FETCHER_FOR_ADMIN']>
const userStore = useUserStore()
const {
  loadTableData,
  reloadTableData,
  openDialog,
  handleSortChange,
  handleSelectChange,
  getTableSelectedRows
} = userStore
const { pageData, loading, queryRequest, table, updateForm, createForm } = storeToRefs(userStore)
onMounted(() => {
  reloadTableData()
})
const handleEdit = (row: { id: string }) => {
  openDialog('UPDATE')
  updateForm.value.id = row.id
}
const handleCreate = () => {
  openDialog('CREATE')
  createForm.value = { ...userStore.initForm }
}
const handleSingleDelete = (row: { id: string }) => {
  handleDelete([row.id])
}
const handleBatchDelete = () => {
  handleDelete(
    getTableSelectedRows().map((row) => {
      return row.id || ''
    })
  )
}
const handleDelete = (ids: string[]) => {
  ElMessageBox.confirm('此操作将删除数据且无法恢复, 是否继续?', '警告', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    api.userForAdminController.delete({ body: ids }).then((res) => {
      assertSuccess(res).then(() => reloadTableData())
    })
  })
}
</script>
<template>
  <div>
    <div class="button-section">
      <el-button type="success" size="small" @click="handleCreate">
        <el-icon>
          <plus />
        </el-icon>
        新增
      </el-button>
      <el-button type="danger" size="small" @click="handleBatchDelete">
        <el-icon>
          <delete />
        </el-icon>
        删除
      </el-button>
    </div>
    <el-table
      ref="table"
      :data="pageData.content"
      :border="true"
      @selection-change="handleSelectChange"
      @sort-change="handleSortChange"
      v-loading="loading"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="手机号" prop="phone" sortable="custom">
        <template v-slot:default="{ row }: UserScope">
          {{ row.phone }}
        </template>
      </el-table-column>
      <el-table-column label="密码" prop="password" sortable="custom">
        <template v-slot:default="{ row }: UserScope">
          {{ row.password }}
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
          <dict-column :dict-id="DictConstants.GENDER" :value="row.gender"></dict-column>
        </template>
      </el-table-column>
      <el-table-column label="状态" prop="status" sortable="custom">
        <template v-slot:default="{ row }: UserScope">
          <dict-column :dict-id="DictConstants.USER_STATUS" :value="row.status"></dict-column>
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
      <el-table-column label="操作" fixed="right" width="{280}">
        <template v-slot:default="{ row }">
          <div>
            <el-button class="edit-btn" link size="small" type="primary" @click="handleEdit(row)">
              <el-icon>
                <edit />
              </el-icon>
            </el-button>
            <el-button
              class="delete-btn"
              link
              size="small"
              type="primary"
              @click="handleSingleDelete(row)"
            >
              <el-icon>
                <delete />
              </el-icon>
            </el-button>
            <el-button
              class="band"
              link
              size="small"
              type="primary"
              @click="handleSingleDelete(row)"
            >
              <el-icon></el-icon>
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <div class="page">
      <el-pagination
        style="margin-top: 30px"
        :current-page="queryRequest.pageNum"
        :page-size="queryRequest.pageSize"
        :page-sizes="[10, 20, 30, 40, 50]"
        :total="pageData.totalElements"
        background
        small
        layout="prev, pager, next, jumper, total, sizes"
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
