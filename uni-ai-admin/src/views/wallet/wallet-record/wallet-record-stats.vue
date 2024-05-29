<script setup lang="ts">
import { walletLabelProp, walletQueryOptions } from '@/views/wallet/wallet/store/wallet-store'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import DictSelect from '@/components/dict/dict-select.vue'
import { useTableHelper } from '@/components/base/table/table-helper'
import { api } from '@/utils/api-instance'
import { onMounted, ref } from 'vue'
import type { WalletRecordType } from '@/apis/__generated/model/enums'
import type { Tuple2 } from '@/apis/__generated/model/static'
import DictColumn from '@/components/dict/dict-column.vue'

const { pageData, queryRequest, loadTableData, loading, reloadTableData } = useTableHelper(
  api.walletRecordForAdminController.groupUser,
  api.walletRecordForAdminController,
  {
    type: 'CHAT'
  }
)
const typeGroupData = ref<Array<Tuple2<WalletRecordType, number>>>([])
onMounted(() => {
  reloadTableData()
  api.walletRecordForAdminController.groupByType().then((res) => {
    typeGroupData.value = res
  })
})
</script>

<template>
  <el-card>
    <el-table style="margin-bottom: 20px" :data="typeGroupData" :border="true">
      <el-table-column label="类型" prop="_1">
        <template #default="{ row }">
          <dict-column :dict-id="DictConstants.WALLET_RECORD_TYPE" :value="row._1"></dict-column>
        </template>
      </el-table-column>
      <el-table-column label="次数" prop="_2"> </el-table-column>
    </el-table>
    <el-form inline size="small">
      <el-form-item label="钱包">
        <remote-select
          placeholder="请输入用户手机号"
          :label-prop="walletLabelProp"
          :query-options="walletQueryOptions"
          v-model="queryRequest.query.walletId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="类型">
        <dict-select
          :dict-id="DictConstants.WALLET_RECORD_TYPE"
          v-model="queryRequest.query.type"
        ></dict-select>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button type="primary" size="small" @click="reloadTableData()"> 查询</el-button>
        </div>
      </el-form-item>
    </el-form>
    <el-table ref="table" :data="pageData.content" :border="true" v-loading="loading">
      <el-table-column label="手机号" prop="_1"></el-table-column>
      <el-table-column label="用户昵称" prop="_2"></el-table-column>
      <el-table-column label="数量" prop="_3"></el-table-column>
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
  </el-card>
</template>

<style scoped lang="scss">
.page {
  display: flex;
  justify-content: flex-end;
}
</style>
