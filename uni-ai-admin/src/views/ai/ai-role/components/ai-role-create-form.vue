<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useAiRoleStore } from '../store/ai-role-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import ImageUpload from '@/components/image/image-upload.vue'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import type { FormInstance, FormRules } from 'element-plus'
import DictSelect from '@/components/dict/dict-select.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import { Close } from '@element-plus/icons-vue'
import { aiModelQueryOptions } from '@/views/ai/ai-model/store/ai-model-store'
import RemoteSelect from '@/components/base/form/remote-select.vue'

const aiRoleStore = useAiRoleStore()
const { closeDialog, reloadTableData } = aiRoleStore
const { createForm, dialogData } = storeToRefs(aiRoleStore)
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof createForm>>({
  name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  prompts: [{ required: true, message: '请输入预置提示词', trigger: 'blur' }]
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
      api.aiRoleForAdminController.create({ body: createForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          closeDialog()
          reloadTableData()
        })
      })
    )
  )
}
const handleAddPrompt = () => {
  createForm.value.prompts.push({ content: [{ text: '' }], type: 'USER' })
}
</script>
<template>
  <div class="create-form">
    <el-form labelWidth="120" class="form" ref="createFormRef" :model="createForm" :rules="rules">
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="createForm.name"></el-input>
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <image-upload v-model="createForm.icon"></image-upload>
      </el-form-item>
      <el-form-item label="模型" prop="aiModelId">
        <remote-select
          label-prop="name"
          :query-options="aiModelQueryOptions"
          v-model="createForm.aiModelId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="预置提示词" prop="prompts">
        <div class="prompts">
          <el-form
            class="prompt"
            v-for="(prompt, index) in createForm.prompts"
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
            <el-icon class="close" @click="createForm.prompts.splice(index, 1)">
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
.create-form {
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
