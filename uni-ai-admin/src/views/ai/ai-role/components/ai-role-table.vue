<script lang="ts" setup>
import {onMounted} from 'vue'
import {storeToRefs} from 'pinia'
import {assertSuccess} from '@/utils/common'
import {api} from '@/utils/api-instance'
import {ElMessageBox} from 'element-plus'
import type {Scope} from '@/typings'
import {useAiRoleStore} from '../store/ai-role-store'
import type {AiRoleDto} from '@/apis/__generated/model/dto'
import {Delete, Edit, Plus} from '@element-plus/icons-vue'

type AiRoleScope = Scope<AiRoleDto['AiRoleRepository/COMPLEX_FETCHER_FOR_ADMIN']>
const aiRoleStore = useAiRoleStore()
const {
  loadTableData,
  reloadTableData,
  openDialog,
  handleSortChange,
  handleSelectChange,
  getTableSelectedRows
} = aiRoleStore
const { pageData, loading, queryRequest, table, updateForm, createForm } = storeToRefs(aiRoleStore)
onMounted(() => {
  reloadTableData()
})
const handleEdit = (row: { id: string }) => {
  openDialog('UPDATE')
  updateForm.value.id = row.id
}
const handleCreate = () => {
  openDialog('CREATE')
  createForm.value = { ...aiRoleStore.initForm }
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
    api.aiRoleForAdminController.delete({ body: ids }).then((res) => {
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
      <el-table-column
        label="角色名称"
        prop="name"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: AiRoleScope">
          {{ row.name }}
        </template>
      </el-table-column>
      <el-table-column
        label="描述"
        prop="description"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: AiRoleScope">
          {{ row.description }}
        </template>
      </el-table-column>
      <el-table-column label="图标" prop="icon" sortable="custom" show-overflow-tooltip width="120">
        <template v-slot:default="{ row }: AiRoleScope">
          <el-avatar :src="row.icon" alt=""></el-avatar>
        </template>
      </el-table-column>
      <el-table-column
        label="预置提示词"
        prop="prompts"
        sortable="custom"
        show-overflow-tooltip
        width="120"
      >
        <template v-slot:default="{ row }: AiRoleScope">
          {{ row.prompts }}
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        prop="createdTime"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: AiRoleScope">
          {{ row.createdTime }}
        </template>
      </el-table-column>
      <el-table-column
        label="更新时间"
        prop="editedTime"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: AiRoleScope">
          {{ row.editedTime }}
        </template>
      </el-table-column>
      <el-table-column
        label="创建人"
        prop="creator.phone"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: AiRoleScope">
          {{ row.creator.nickname }}({{ row.creator.phone }})
        </template>
      </el-table-column>
      <el-table-column
        label="更新人"
        prop="editor.phone"
        sortable="custom"
        show-overflow-tooltip
        width="150"
      >
        <template v-slot:default="{ row }: AiRoleScope">
          {{ row.editor.nickname }}({{ row.editor.phone }})
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right">
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
