<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useAiRoleStore } from '../store/ai-role-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import ImageUpload from '@/components/image/image-upload.vue'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import type { FormInstance, FormRules } from 'element-plus'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import { Close } from '@element-plus/icons-vue'
import DictSelect from '@/components/dict/dict-select.vue'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { aiModelQueryOptions } from '@/views/ai/ai-model/store/ai-model-store'

const aiRoleStore = useAiRoleStore()
const { closeDialog, reloadTableData } = aiRoleStore
const { updateForm, dialogData } = storeToRefs(aiRoleStore)
const updateFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof updateForm>>({
  name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  prompts: [{ required: true, message: '请输入预置提示词', trigger: 'blur' }]
})
const init = async () => {
  dialogData.value.title = '编辑'
  updateForm.value = {
    ...(await api.aiRoleForAdminController.findById({ id: updateForm.value.id || '' }))
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
      api.aiRoleForAdminController.update({ body: updateForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          closeDialog()
          reloadTableData()
        })
      })
    })
  )
}
const handleAddPrompt = () => {
  updateForm.value.prompts.push({ content: [{ text: '' }], type: 'USER' })
}
</script>
<template>
  <div class="update-form">
    <el-form labelWidth="120" class="form" ref="updateFormRef" :model="updateForm" :rules="rules">
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="updateForm.name"></el-input>
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <image-upload v-model="updateForm.icon"></image-upload>
      </el-form-item>
      <el-form-item label="模型" prop="aiModelId">
        <remote-select
          label-prop="name"
          :query-options="aiModelQueryOptions"
          v-model="updateForm.aiModelId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="预置提示词" prop="prompts">
        <div class="prompts">
          <el-form
            class="prompt"
            v-for="(prompt, index) in updateForm.prompts"
            :key="index"
            size="small"
          >
            <el-form-item label="角色">
              <dict-select
                :dict-id="DictConstants.AI_MESSAGE_TYPE"
                v-model="prompt.type"
              ></dict-select>
            </el-form-item>
            <el-form-item label="消息">
              <el-input type="textarea" v-model="prompt.content[0]['text']"></el-input>
            </el-form-item>
            <el-icon class="close" @click="updateForm.prompts.splice(index, 1)">
              <close></close>
            </el-icon>
          </el-form>
          <el-button @click="handleAddPrompt" size="small" type="primary">添加提示词</el-button>
        </div>
      </el-form-item>
    </el-form>
    <footer-button @close="closeDialog" @confirm="handleConfirm"></footer-button>
  </div>
</template>

<style lang="scss" scoped>
.update-form {
  margin-right: 30px;
  .prompts {
    .prompt {
      position: relative;
      margin: 10px 0;
      .el-form-item {
        margin-bottom: 10px;
      }
      .close {
        position: absolute;
        top: 0;
        right: -20px;
      }
    }
  }
}
</style>
