<script setup lang="ts">
import { reactive, ref } from 'vue'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import { Link } from '@element-plus/icons-vue'
import type { AiSessionCreateInput } from '@/apis/__generated/model/static'
import { useAiChatStore } from '@/views/ai/ai-chat/store/ai-chat-store'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { aiModelQueryOptions } from '@/views/ai/ai-model/store/ai-model-store'
import { aiRoleQueryOptions } from '@/views/ai/ai-role/store/ai-role-store'
import type { AiModelDto } from '@/apis/__generated/model/dto'
import { Dictionaries } from '@/apis/__generated/model/enums/DictConstants'
import type { FormInstance, FormRules } from 'element-plus'

const createForm = ref<AiSessionCreateInput>({ aiModelId: '', name: '' })
const visible = ref(false)
const chatStore = useAiChatStore()
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<AiSessionCreateInput>>({
  name: [{ required: true, message: '请输入会话名称', trigger: 'blur' }],
  aiModelId: [{ required: true, message: '请选择模型', trigger: 'blur' }]
})
const handleConfirm = async () => {
  await chatStore.handleCreateSession(createForm.value)
  closeDialog()
}
const closeDialog = () => {
  visible.value = false
}
const modelLabelProp = (row: AiModelDto['AiModelRepository/COMPLEX_FETCHER_FOR_ADMIN']) => {
  const tags = row.tagsView.map((tag) => Dictionaries.AiModelTag[tag.name].keyName).join('、')
  return `${row.name}(${Dictionaries.AiFactoryType[row.aiFactory.name].keyName}-${tags})`
}
</script>

<template>
  <div>
    <el-button size="small" :icon="Link" @click="visible = true">创建会话</el-button>
    <el-dialog v-model="visible" append-to-body>
      <el-form :rules="rules" :model="createForm" ref="createFormRef" size="small">
        <el-form-item label="会话名称" prop="name">
          <el-input v-model="createForm.name"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <remote-select
            label-prop="name"
            :query-options="aiRoleQueryOptions"
            v-model="createForm.aiRoleId"
          ></remote-select>
        </el-form-item>
        <el-form-item label="模型" prop="aiModelId">
          <remote-select
            :label-prop="modelLabelProp"
            :query-options="
              (query, id) => aiModelQueryOptions(query, id, ['AIGC', 'VISION', 'IMAGE'])
            "
            v-model="createForm.aiModelId"
          ></remote-select>
        </el-form-item>
      </el-form>
      <footer-button @close="closeDialog" @confirm="handleConfirm"></footer-button>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss"></style>
