<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useAiDocumentStore } from '../store/ai-document-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { Result } from '@/typings'
import { Plus } from '@element-plus/icons-vue'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { aiModelQueryOptions } from '@/views/ai/ai-model/store/ai-model-store'
import { aiCollectionQueryOptions } from '@/views/ai/ai-collection/store/ai-collection-store'
const API_PREFIX = import.meta.env.VITE_API_PREFIX
const loading = ref(false)
const aiDocumentStore = useAiDocumentStore()
const { closeDialog, reloadTableData } = aiDocumentStore
const { createForm, dialogData } = storeToRefs(aiDocumentStore)
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof createForm>>({
  name: [{ required: true, message: '请输入文档名称', trigger: 'blur' }],
  url: [{ required: true, message: '请输入文档链接', trigger: 'blur' }],
  aiCollectionId: [{ required: true, message: '请选择知识库)', trigger: 'change' }],
  summaryModelId: [{ required: true, message: '请选择总结模型)', trigger: 'change' }]
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
      api.aiDocumentForAdminController.create({ body: createForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          loading.value = false
          closeDialog()
          reloadTableData()
        })
      })
    })
  )
}
const onUploadSuccess = (res: Result<{ url: string }>) => {
  createForm.value.url = res.result.url
}
</script>
<template>
  <div class="create-form">
    <el-form labelWidth="120" class="form" ref="createFormRef" :model="createForm" :rules="rules">
      <el-form-item label="名称" prop="name">
        <el-input v-model="createForm.name"></el-input>
      </el-form-item>
      <el-form-item label="文档链接" prop="url">
        <el-upload
          :action="`${API_PREFIX}/oss/upload`"
          show-file-list
          :limit="1"
          :on-success="onUploadSuccess"
        >
          <el-icon> <plus></plus> </el-icon
        ></el-upload>
      </el-form-item>
      <el-form-item label="知识库" prop="aiCollectionId">
        <remote-select
          label-prop="name"
          :query-options="aiCollectionQueryOptions"
          v-model="createForm.aiCollectionId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="总结模型" prop="summaryModelId">
        <remote-select
          label-prop="name"
          :query-options="
            (query, id) => {
              return aiModelQueryOptions(query, id, ['AIGC'])
            }
          "
          v-model="createForm.summaryModelId"
        ></remote-select>
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
