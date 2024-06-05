<script lang="ts" setup>
import {storeToRefs} from 'pinia'
import {reactive, ref, watch} from 'vue'
import {useAiSessionStore} from '../store/ai-session-store'
import {assertFormValidate, assertSuccess} from '@/utils/common'
import {api} from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import type {FormInstance, FormRules} from 'element-plus'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import {aiRoleQueryOptions} from '@/views/ai/ai-role/store/ai-role-store'

const aiSessionStore = useAiSessionStore()
const { closeDialog, reloadTableData } = aiSessionStore
const { updateForm, dialogData } = storeToRefs(aiSessionStore)
const updateFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof updateForm>>({
  name: [{ required: true, message: '请输入会话名称', trigger: 'blur' }],
  aiRoleId: [{ required: true, message: '请输入角色', trigger: 'blur' }]
})
const init = async () => {
  dialogData.value.title = '编辑'
  updateForm.value = {
    ...(await api.aiSessionForAdminController.findById({ id: updateForm.value.id || '' }))
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
      api.aiSessionForAdminController.update({ body: updateForm.value }).then(async (res) => {
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
      <el-form-item label="会话名称" prop="name">
        <el-input v-model="updateForm.name"></el-input>
      </el-form-item>
      <el-form-item label="角色" prop="aiRoleId">
        <remote-select
          label-prop="name"
          :query-options="aiRoleQueryOptions"
          v-model="updateForm.aiRoleId"
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
