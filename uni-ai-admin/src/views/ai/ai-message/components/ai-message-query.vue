<script lang="ts" setup>
import { toRefs } from 'vue'
import { useAiMessageStore } from '../store/ai-message-store'
import { storeToRefs } from 'pinia'
import DictSelect from '@/components/dict/dict-select.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { aiSessionQueryOptions } from '@/views/ai/ai-session/store/ai-session-store'

const aiMessageStore = useAiMessageStore()
const { queryData } = storeToRefs(aiMessageStore)
const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <el-form-item label="消息类型">
        <dict-select :dict-id="DictConstants.AI_MESSAGE_TYPE" v-model="query.type"></dict-select>
      </el-form-item>
      <el-form-item label="消息内容">
        <el-input v-model="query.content"></el-input>
      </el-form-item>
      <el-form-item label="会话">
        <remote-select
          label-prop="name"
          :query-options="aiSessionQueryOptions"
          v-model="query.aiSessionId"
        ></remote-select>
      </el-form-item>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
            type="primary"
            size="small"
            @click="aiMessageStore.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="aiMessageStore.restQuery()">
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
