<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useAiPluginStore } from '../store/ai-plugin-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'

const aiPluginStore = useAiPluginStore()
const { closeDialog, reloadTableData } = aiPluginStore
const { createForm, dialogData } = storeToRefs(aiPluginStore)
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof createForm>>({
  name: [{ required: true, message: '请输入函数名称', trigger: 'blur' }],
  description: [{ required: true, message: '请输入函数描述', trigger: 'blur' }]
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
      api.aiPluginForAdminController.create({ body: createForm.value }).then(async (res) => {
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
      <el-form-item label="函数名称" prop="name">
        <el-input v-model="createForm.name"></el-input>
      </el-form-item>
      <el-form-item label="函数描述" prop="description">
        <el-input v-model="createForm.description"></el-input>
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
