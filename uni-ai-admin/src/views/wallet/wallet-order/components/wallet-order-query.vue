<script lang="ts" setup>
import { toRefs } from 'vue'
import { useWalletOrderStore } from '../store/wallet-order-store'
import { storeToRefs } from 'pinia'
import DictSelect from '@/components/dict/dict-select.vue'
import DatetimePicker from '@/components/datetime/datetime-picker.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import { userLabelProp, userQueryOptions } from '@/views/user/store/user-store'
import RemoteSelect from '@/components/base/form/remote-select.vue'
const walletOrderStore = useWalletOrderStore()
const { queryData } = storeToRefs(walletOrderStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="支付金额">
        <el-input-number v-model="query.amount" controls-position="right"></el-input-number>
      </el-form-item>
      <el-form-item label="支付时间">
        <datetime-picker
          v-model:min-date-time="query.minPayTime"
          v-model:max-date-time="query.maxPayTime"
        >
        </datetime-picker>
      </el-form-item>
      <el-form-item label="用户">
        <remote-select
          v-model="query.userId"
          :label-prop="userLabelProp"
          :query-options="userQueryOptions"
        ></remote-select>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="walletOrderStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="walletOrderStore.restQuery">
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
