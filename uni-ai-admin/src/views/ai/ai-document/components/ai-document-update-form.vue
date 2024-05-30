<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useAiDocumentStore } from '../store/ai-document-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import type { FormInstance, FormRules } from 'element-plus'

const aiDocumentStore = useAiDocumentStore()
const { closeDialog, reloadTableData } = aiDocumentStore
const { updateForm, dialogData } = storeToRefs(aiDocumentStore)
const updateFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof updateForm>>({
  url: [{ required: true, message: '请输入文档链接', trigger: 'blur' }],
  collection: [{ required: true, message: '请输入集合名称(表名)', trigger: 'blur' }],
  summary: [{ required: true, message: '请输入总结', trigger: 'blur' }]
})
const init = async () => {
  dialogData.value.title = '编辑'
  updateForm.value = {
    ...(await api.aiDocumentForAdminController.findById({ id: updateForm.value.id || '' }))
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
      api.aiDocumentForAdminController.update({ body: updateForm.value }).then(async (res) => {
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
      <el-form-item label="文档链接" prop="url">
        <el-input v-model="updateForm.url"></el-input>
      </el-form-item>
      <el-form-item label="集合名称(表名)" prop="collection">
        <el-input v-model="updateForm.collection"></el-input>
      </el-form-item>
      <el-form-item label="总结" prop="summary">
        <el-input v-model="updateForm.summary"></el-input>
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
