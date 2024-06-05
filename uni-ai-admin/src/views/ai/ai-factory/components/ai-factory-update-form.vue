<script lang="ts" setup>
import {storeToRefs} from 'pinia'
import {reactive, ref, watch} from 'vue'
import {useAiFactoryStore} from '../store/ai-factory-store'
import {assertFormValidate, assertSuccess} from '@/utils/common'
import {api} from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import DictSelect from '@/components/dict/dict-select.vue'
import type {FormInstance, FormRules} from 'element-plus'
import {DictConstants} from '@/apis/__generated/model/enums/DictConstants'
import Options from '@/views/ai/ai-factory/components/options/options.vue'

const aiFactoryStore = useAiFactoryStore()
const { closeDialog, reloadTableData } = aiFactoryStore
const { updateForm, dialogData } = storeToRefs(aiFactoryStore)
const updateFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof updateForm>>({
  name: [{ required: true, message: '请输入厂家名称', trigger: 'change' }]
})
const init = async () => {
  dialogData.value.title = '编辑'
  updateForm.value = {
    ...(await api.aiFactoryForFrontController.findById({ id: updateForm.value.id || '' }))
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
      api.aiFactoryForFrontController.update({ body: updateForm.value }).then(async (res) => {
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
      <el-form-item label="厂家名称" prop="name">
        <dict-select
          :key="updateForm.id"
          :dict-id="DictConstants.AI_FACTORY_TYPE"
          v-model="updateForm.name"
          disabled
        ></dict-select>
      </el-form-item>
      <el-form-item label="厂家描述" prop="description">
        <el-input v-model="updateForm.description"></el-input>
      </el-form-item>
      <options
        :key="updateForm.id"
        :factory="updateForm.name"
        v-model="updateForm.options"
      ></options>
    </el-form>
    <footer-button @close="closeDialog" @confirm="handleConfirm"></footer-button>
  </div>
</template>

<style lang="scss" scoped>
.update-form {
  margin-right: 30px;
}
</style>
