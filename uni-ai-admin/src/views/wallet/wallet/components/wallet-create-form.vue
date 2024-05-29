<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useWalletStore } from '../store/wallet-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import ImageUpload from '@/components/image/image-upload.vue'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import DictSelect from '@/components/dict/dict-select.vue'
import type { FormInstance, FormRules } from 'element-plus'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import { userLabelProp, userQueryOptions } from '@/views/user/store/user-store'
import RemoteSelect from '@/components/base/form/remote-select.vue'
const walletStore = useWalletStore()
const { closeDialog, reloadTableData } = walletStore
const { createForm, dialogData } = storeToRefs(walletStore)
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof createForm>>({
  password: [{ required: true, message: '请输入钱包密码', trigger: 'blur' }],
  userId: [{ required: true, message: '请输入用户', trigger: 'blur' }]
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
      api.walletForAdminController.create({ body: createForm.value }).then(async (res) => {
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
      <el-form-item label="余额" prop="balance">
        <el-input-number v-model="createForm.balance"></el-input-number>
      </el-form-item>
      <el-form-item label="钱包密码" prop="password">
        <el-input v-model="createForm.password"></el-input>
      </el-form-item>
      <el-form-item label="用户" prop="userId">
        <remote-select
          v-model="createForm.userId"
          :label-prop="userLabelProp"
          :query-options="userQueryOptions"
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
