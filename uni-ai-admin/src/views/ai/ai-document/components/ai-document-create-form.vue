<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useAiDocumentStore } from '../store/ai-document-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import type { FormInstance, FormRules } from 'element-plus'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { aiCollectionQueryOptions } from '@/views/ai/ai-collection/store/ai-collection-store'
import type { Result } from '@/typings'

const API_PREFIX = import.meta.env.VITE_API_PREFIX
const loading = ref(false)
const aiDocumentStore = useAiDocumentStore()
const { closeDialog, reloadTableData } = aiDocumentStore
const { createForm, dialogData } = storeToRefs(aiDocumentStore)
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof createForm>>({
  aiCollectionId: [{ required: true, message: '请输入知识库', trigger: 'blur' }],
  name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
  content: [{ required: true, message: '请输入内容', trigger: 'blur' }]
})
const init = async () => {
  dialogData.value.title = '创建'
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
  createFormRef.value?.validate(
    assertFormValidate(() => {
      loading.value = true
      api.aiDocumentForFrontController.create({ body: createForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          loading.value = false
          closeDialog()
          reloadTableData()
        })
      })
    })
  )
}
const onUploadSuccess = (res: Result<string>) => {
  createForm.value.content = res.result
}
</script>
<template>
  <div class="create-form">
    <el-form
      labelWidth="120"
      class="form"
      ref="createFormRef"
      :model="createForm"
      :rules="rules"
      v-loading="loading"
    >
      <el-form-item label="知识库" prop="aiCollectionId">
        <remote-select
          label-prop="name"
          :query-options="aiCollectionQueryOptions"
          v-model="createForm.aiCollectionId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input v-model="createForm.name"></el-input>
      </el-form-item>
      <el-form-item label="文档解析" prop="url">
        <el-upload
          :action="`${API_PREFIX}/front/ai-document/extract`"
          show-file-list
          :limit="1"
          :on-success="onUploadSuccess"
        >
          <el-icon> <plus></plus> </el-icon
        ></el-upload>
      </el-form-item>
      <el-form-item label="内容" prop="content">
        <el-input
          type="textarea"
          :autosize="{ maxRows: 25 }"
          v-model="createForm.content"
        ></el-input>
      </el-form-item>
    </el-form>
    <footer-button @close="closeDialog" @confirm="handleConfirm"></footer-button>
  </div>
</template>

<style lang="scss" scoped>
.create-form {
  margin-right: 30px;
}
</style>
