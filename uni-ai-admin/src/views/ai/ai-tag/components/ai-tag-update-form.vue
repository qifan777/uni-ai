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
const { updateForm, dialogData } = storeToRefs(aiTagStore)
const updateFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof updateForm>>({
  name: [{ required: true, message: '请输入标签', trigger: 'change' }],
  factory: [{ required: true, message: '请输入厂家', trigger: 'change' }],
  service: [{ required: true, message: '请输入service', trigger: 'blur' }]
})
const init = async () => {
  dialogData.value.title = '编辑'
  updateForm.value = {
    ...(await api.aiTagForAdminController.findById({ id: updateForm.value.id || '' }))
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
      api.aiTagForAdminController.update({ body: updateForm.value }).then(async (res) => {
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
      <el-form-item label="标签" prop="name">
        <dict-select :dict-id="DictConstants.AI_MODEL_TAG" v-model="updateForm.name"></dict-select>
      </el-form-item>
      <el-form-item label="厂家" prop="factory">
        <dict-select :dict-id="DictConstants.AI_FACTORY_TYPE" v-model="updateForm.factory" />
      </el-form-item>
      <el-form-item label="service" prop="service">
        <el-input v-model="updateForm.service"></el-input>
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
