<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useAiMessageStore } from '../store/ai-message-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import DictSelect from '@/components/dict/dict-select.vue'
import type { FormInstance, FormRules } from 'element-plus'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { aiSessionQueryOptions } from '@/views/ai/ai-session/store/ai-session-store'

const aiMessageStore = useAiMessageStore()
const { closeDialog, reloadTableData } = aiMessageStore
const { updateForm, dialogData } = storeToRefs(aiMessageStore)
const updateFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof updateForm>>({
  content: [{ required: true, message: '请输入消息内容', trigger: 'blur' }],
  aiSessionId: [{ required: true, message: '请输入会话', trigger: 'blur' }]
})
const init = async () => {
  dialogData.value.title = '编辑'
  updateForm.value = {
    ...(await api.aiMessageForAdminController.findById({ id: updateForm.value.id || '' }))
  }
}
watch(
  () => dialogData.value.visible,
  (value) => {
    if (value) {
      init()
    }
  },
  { immediate: true }
)
const handleConfirm = () => {
  updateFormRef.value?.validate(
    assertFormValidate(() => {
      api.aiMessageForAdminController.update({ body: updateForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          closeDialog()
          reloadTableData()
        })
      })
    })
  )
}
</script>
<template>
  <div class="update-form">
    <el-form labelWidth="120" class="form" ref="updateFormRef" :model="updateForm" :rules="rules">
      <el-form-item label="消息类型" prop="type">
        <dict-select
          :dict-id="DictConstants.AI_MESSAGE_TYPE"
          v-model="updateForm.type"
        ></dict-select>
      </el-form-item>
      <el-form-item label="消息内容" prop="content">
        <el-input v-model="updateForm.content" type="textarea"></el-input>
      </el-form-item>
      <el-form-item label="会话" prop="aiSessionId">
        <remote-select
          label-prop="name"
          :query-options="aiSessionQueryOptions"
          v-model="updateForm.aiSessionId"
        ></remote-select>
      </el-form-item>
    </el-form>
    <footer-button @close="closeDialog" @confirm="handleConfirm"></footer-button>
  </div>
</template>

<style lang="scss" scoped>
.update-form {
  margin-right: 30px;
}
</style>
