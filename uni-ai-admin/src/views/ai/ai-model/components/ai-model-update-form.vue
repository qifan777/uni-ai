<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useAiModelStore } from '../store/ai-model-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import type { FormInstance, FormRules } from 'element-plus'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import EmbeddingOptions from '@/views/ai/ai-model/components/embedding-options/embedding-options.vue'
import ChatOptions from '@/views/ai/ai-model/components/chat-options/chat-options.vue'
import type { AiFactoryDto, AiTagDto } from '@/apis/__generated/model/dto'
import type { AiTagSpec } from '@/apis/__generated/model/static'
import { recursiveOmit } from '@/components/base/table/table-helper'
import type { AiModelTag } from '@/apis/__generated/model/enums'
import ImageOptions from '@/views/ai/ai-model/components/image-options/image-options.vue'

const aiModelStore = useAiModelStore()
const { closeDialog, reloadTableData } = aiModelStore
const { updateForm, dialogData } = storeToRefs(aiModelStore)
const updateFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof updateForm>>({
  aiFactoryId: [{ required: true, message: '请输入厂家', trigger: 'blur' }],
  name: [{ required: true, message: '请输入模型', trigger: 'blur' }]
})
const init = async () => {
  dialogData.value.title = '编辑'
  const res = await api.aiModelForAdminController.findById({ id: updateForm.value.id || '' })
  updateForm.value = {
    ...res,
    tagIds: res.tagsView.map((tag) => tag.id)
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
  updateForm.value.options = { model: updateForm.value.name, ...updateForm.value.options }
  updateFormRef.value?.validate(
    assertFormValidate(() => {
      api.aiModelForAdminController.update({ body: updateForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          closeDialog()
          reloadTableData()
        })
      })
    })
  )
}
const tags = ref<AiTagDto['AiTagRepository/COMPLEX_FETCHER_FOR_ADMIN'][]>([])
const aiTagQueryOptions = async (_keyword: string, ids: string[]) => {
  const query: AiTagSpec = { ids, aiFactoryId: updateForm.value.aiFactoryId }
  const values = (
    await api.aiTagForAdminController.query({ body: { query: recursiveOmit(query) as AiTagSpec } })
  ).content

  if (ids && ids.length > 0) {
    tags.value = values
  }
  return values
}
const hasTag = (tagName: AiModelTag) => {
  return tags.value.filter((tag) => tag.name === tagName).length > 0
}
const factory = ref<AiFactoryDto['AiFactoryRepository/COMPLEX_FETCHER_FOR_ADMIN']>()
const aiFactoryQueryOptions = async (_keyword: string, id: string) => {
  const res = (await api.aiFactoryForAdminController.query({ body: { query: { id } } })).content
  if (id && res.length > 0) {
    factory.value = res[0]
  }
  return res
}
</script>
<template>
  <div class="update-form">
    <el-form labelWidth="120" class="form" ref="updateFormRef" :model="updateForm" :rules="rules">
      <el-form-item label="厂家" prop="aiFactoryId">
        <remote-select
          label-prop="name"
          :query-options="aiFactoryQueryOptions"
          v-model="updateForm.aiFactoryId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="模型" prop="name">
        <el-input v-model="updateForm.name"></el-input>
      </el-form-item>
      <el-form-item label="标签">
        <remote-select
          :key="updateForm.aiFactoryId"
          label-prop="name"
          :query-options="aiTagQueryOptions"
          v-model="updateForm.tagIds"
          multiple
        ></remote-select>
      </el-form-item>
      <chat-options
        :factory="factory.name"
        v-model="updateForm.options"
        v-if="hasTag('AIGC') && factory"
      ></chat-options>
      <embedding-options
        :factory="factory.name"
        v-model="updateForm.options"
        v-if="hasTag('EMBEDDINGS') && factory"
      ></embedding-options>
      <image-options
        :factory="factory.name"
        v-model="updateForm.options"
        v-if="hasTag('IMAGE') && factory"
      ></image-options>
    </el-form>
    <footer-button @close="closeDialog" @confirm="handleConfirm"></footer-button>
  </div>
</template>

<style lang="scss" scoped>
.update-form {
  margin-right: 30px;
}
</style>
