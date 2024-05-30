<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useAiCollectionStore } from '../store/ai-collection-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import type { FormInstance, FormRules } from 'element-plus'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { aiModelQueryOptions } from '@/views/ai/ai-model/store/ai-model-store'

const aiCollectionStore = useAiCollectionStore()
const { closeDialog, reloadTableData } = aiCollectionStore
const { updateForm, dialogData } = storeToRefs(aiCollectionStore)
const updateFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof updateForm>>({
  name: [{ required: true, message: '请输入中文名称', trigger: 'blur' }],
  collectionName: [{ required: true, message: '请输入英文名称', trigger: 'blur' }],
  embeddingModelId: [{ required: true, message: '请输入嵌入模型', trigger: 'blur' }]
})
const init = async () => {
  dialogData.value.title = '编辑'
  updateForm.value = {
    ...(await api.aiCollectionForAdminController.findById({ id: updateForm.value.id || '' }))
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
      api.aiCollectionForAdminController.update({ body: updateForm.value }).then(async (res) => {
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
      <el-form-item label="名称" prop="name">
        <el-input v-model="updateForm.name"></el-input>
      </el-form-item>
      <el-form-item label="英文名称" prop="collectionName">
        <el-input v-model="updateForm.collectionName"></el-input>
      </el-form-item>
      <el-form-item label="嵌入模型" prop="embeddingModelId">
        <remote-select
          label-prop="name"
          :query-options="(query, id) => aiModelQueryOptions(query, id, ['EMBEDDINGS'])"
          v-model="updateForm.embeddingModelId"
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
