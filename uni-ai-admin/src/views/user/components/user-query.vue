<script lang="ts" setup>
import { toRefs } from 'vue'
import { useUserStore } from '../store/user-store'
import { storeToRefs } from 'pinia'
import DictSelect from '@/components/dict/dict-select.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'

const userStore = useUserStore()
const { queryData } = storeToRefs(userStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="手机号">
        <el-input v-model="query.phone"></el-input>
      </el-form-item>
      <el-form-item label="密码">
        <el-input v-model="query.password"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="query.nickname"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <dict-select :dict-id="DictConstants.GENDER" v-model="query.gender"></dict-select>
      </el-form-item>
      <el-form-item label="状态">
        <dict-select :dict-id="DictConstants.USER_STATUS" v-model="query.status"></dict-select>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="userStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="userStore.restQuery()"> 重置</el-button>
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
