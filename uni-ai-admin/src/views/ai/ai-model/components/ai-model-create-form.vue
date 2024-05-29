<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useAiModelStore } from '../store/ai-model-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import DictSelect from '@/components/dict/dict-select.vue'
import type { FormInstance, FormRules } from 'element-plus'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import type { AiModelTag } from '@/apis/__generated/model/enums'
import { recursiveOmit } from '@/components/base/table/table-helper'
import type { AiTagSpec } from '@/apis/__generated/model/static'
import ChatOption from '@/views/ai/ai-model/components/options/chat-option.vue'

const aiModelStore = useAiModelStore()
const { closeDialog, reloadTableData } = aiModelStore
const { createForm, dialogData } = storeToRefs(aiModelStore)
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof createForm>>({
  factory: [{ required: true, message: '请输入厂家', trigger: 'change' }],
  name: [{ required: true, message: '请输入模型', trigger: 'blur' }]
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
  createForm.value.options = { model: createForm.value.name, ...createForm.value.options }
  createFormRef.value?.validate(
    assertFormValidate(() =>
      api.aiModelForAdminController.create({ body: createForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          closeDialog()
          reloadTableData()
        })
      })
    )
  )
}
const aiTagQueryOptions = async (_keyword: string, ids: string[]) => {
  const query: AiTagSpec = { ids, factory: createForm.value.factory }
  return (
    await api.aiTagForAdminController.query({ body: { query: recursiveOmit(query) as AiTagSpec } })
  ).content
}
</script>
<template>
  <div class="create-form">
    <el-form labelWidth="120" class="form" ref="createFormRef" :model="createForm" :rules="rules">
      <el-form-item label="厂家" prop="factory">
        <dict-select
          :dict-id="DictConstants.AI_FACTORY_TYPE"
          v-model="createForm.factory"
        ></dict-select>
      </el-form-item>
      <el-form-item label="模型" prop="name">
        <el-input v-model="createForm.name"></el-input>
      </el-form-item>
      <el-form-item label="标签">
        <remote-select
          :key="createForm.factory"
          label-prop="name"
          :query-options="aiTagQueryOptions"
          v-model="createForm.tagIds"
          multiple
        ></remote-select>
      </el-form-item>
      <chat-option :factory="createForm.factory" v-model="createForm.options"></chat-option>
    </el-form>
    <footer-button @close="closeDialog" @confirm="handleConfirm"></footer-button>
  </div>
</template>

<style lang="scss" scoped>
.create-form {
  margin-right: 30px;
}
</style>
