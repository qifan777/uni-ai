<script lang="ts" setup>
import { toRefs } from 'vue'
import { useWalletRecordStore } from '../store/wallet-record-store'
import { storeToRefs } from 'pinia'
import DictSelect from '@/components/dict/dict-select.vue'
import DatetimePicker from '@/components/datetime/datetime-picker.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import { userLabelProp, userQueryOptions } from '@/views/user/store/user-store'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { walletLabelProp, walletQueryOptions } from '@/views/wallet/wallet/store/wallet-store'
const walletRecordStore = useWalletRecordStore()
const { queryData } = storeToRefs(walletRecordStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="钱包">
        <remote-select
          placeholder="请输入用户手机号"
          :label-prop="walletLabelProp"
          :query-options="walletQueryOptions"
          v-model="query.walletId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="操作金额">
        <el-input-number v-model="query.amount" controls-position="right"></el-input-number>
      </el-form-item>
      <el-form-item label="钱包当前余额">
        <el-input-number v-model="query.balance" controls-position="right"></el-input-number>
      </el-form-item>
      <el-form-item label="类型">
        <dict-select :dict-id="DictConstants.WALLET_RECORD_TYPE" v-model="query.type"></dict-select>
      </el-form-item>
      <el-form-item label="描述信息">
        <el-input v-model="query.description"></el-input>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="walletRecordStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="walletRecordStore.restQuery">
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
