<script setup lang="ts">
import { ref } from 'vue'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import { Link } from '@element-plus/icons-vue'
import type { AiSessionCreateInput } from '@/apis/__generated/model/static'
import { useAiChatStore } from '@/views/ai/ai-chat/store/ai-chat-store'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { aiModelQueryOptions } from '@/views/ai/ai-model/store/ai-model-store'
import { aiRoleQueryOptions } from '@/views/ai/ai-role/store/ai-role-store'

const createForm = ref<AiSessionCreateInput>({ name: '' })
const visible = ref(false)
const chatStore = useAiChatStore()

const handleConfirm = async () => {
  await chatStore.handleCreateSession(createForm.value)
  closeDialog()
}
const closeDialog = () => {
  visible.value = false
}
</script>

<template>
  <div>
    <el-button size="small" :icon="Link" @click="visible = true">创建会话</el-button>
    <el-dialog v-model="visible" append-to-body>
      <el-form size="small">
        <el-form-item label="会话名称">
          <el-input v-model="createForm.name"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <remote-select
            label-prop="name"
            :query-options="aiRoleQueryOptions"
            v-model="createForm.aiRoleId"
          ></remote-select>
        </el-form-item>
        <el-form-item label="模型">
          <remote-select
            label-prop="name"
            :query-options="(query, id) => aiModelQueryOptions(query, id, ['AIGC', 'VISION'])"
            v-model="createForm.aiModelId"
          ></remote-select>
        </el-form-item>
      </el-form>
      <footer-button @close="closeDialog" @confirm="handleConfirm"></footer-button>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss"></style>
