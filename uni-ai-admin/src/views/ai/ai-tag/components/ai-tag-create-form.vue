<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useAiTagStore } from '../store/ai-tag-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import DictSelect from '@/components/dict/dict-select.vue'
import type { FormInstance, FormRules } from 'element-plus'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'

const aiTagStore = useAiTagStore()
const { closeDialog, reloadTableData } = aiTagStore
const { createForm, dialogData } = storeToRefs(aiTagStore)
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof createForm>>({
  name: [{ required: true, message: '请输入标签', trigger: 'change' }],
  factory: [{ required: true, message: '请输入厂家', trigger: 'change' }],
  springAiModel: [{ required: true, message: '请输入SpringAIModel', trigger: 'blur' }]
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
      api.aiTagForAdminController.create({ body: createForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          closeDialog()
          reloadTableData()
        })
      })
    )
  )
}
</script>
<template>
  <div class="create-form">
    <el-form labelWidth="120" class="form" ref="createFormRef" :model="createForm" :rules="rules">
      <el-form-item label="标签" prop="name">
        <dict-select :dict-id="DictConstants.AI_MODEL_TAG" v-model="createForm.name"></dict-select>
      </el-form-item>
      <el-form-item label="厂家" prop="factory">
        <dict-select
          :dict-id="DictConstants.AI_FACTORY_TYPE"
          v-model="createForm.factory"
        ></dict-select>
      </el-form-item>
      <el-form-item label="SpringAIModel" prop="springAiModel">
        <el-input v-model="createForm.springAiModel"></el-input>
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
