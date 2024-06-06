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
import type { AiModelDto } from '@/apis/__generated/model/dto'
import { Dictionaries } from '@/apis/__generated/model/enums/DictConstants'

const aiCollectionStore = useAiCollectionStore()
const { closeDialog, reloadTableData } = aiCollectionStore
const { createForm, dialogData } = storeToRefs(aiCollectionStore)
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof createForm>>({
  name: [{ required: true, message: '请输入中文名称', trigger: 'blur' }],
  collectionName: [{ required: true, message: '请输入英文名称', trigger: 'blur' }],
  embeddingModelId: [{ required: true, message: '请输入嵌入模型', trigger: 'blur' }]
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
    assertFormValidate(() =>
      api.aiCollectionForFrontController.create({ body: createForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          closeDialog()
          reloadTableData()
        })
      })
    )
  )
}
const modelLabelProp = (row: AiModelDto['AiModelRepository/COMPLEX_FETCHER_FOR_ADMIN']) => {
  const tags = row.tagsView.map((tag) => Dictionaries.AiModelTag[tag.name].keyName).join('、')
  return `${row.name}(${Dictionaries.AiFactoryType[row.factory].keyName}-${tags})`
}
</script>
<template>
  <div class="create-form">
    <el-form labelWidth="120" class="form" ref="createFormRef" :model="createForm" :rules="rules">
      <el-form-item label="名称" prop="name">
        <el-input v-model="createForm.name"></el-input>
      </el-form-item>
      <el-form-item label="英文名称(仅支持字母和_)" prop="collectionName">
        <el-input v-model="createForm.collectionName"></el-input>
      </el-form-item>
      <el-form-item label="嵌入模型" prop="embeddingModelId">
        <remote-select
          :label-prop="modelLabelProp"
          :query-options="(query, id) => aiModelQueryOptions(query, id, ['EMBEDDINGS'])"
          v-model="createForm.embeddingModelId"
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
