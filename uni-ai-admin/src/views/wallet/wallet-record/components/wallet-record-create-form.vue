<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useWalletRecordStore } from '../store/wallet-record-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import ImageUpload from '@/components/image/image-upload.vue'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import DictSelect from '@/components/dict/dict-select.vue'
import type { FormInstance, FormRules } from 'element-plus'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import { userLabelProp, userQueryOptions } from '@/views/user/store/user-store'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { walletLabelProp, walletQueryOptions } from '@/views/wallet/wallet/store/wallet-store'
const walletRecordStore = useWalletRecordStore()
const { closeDialog, reloadTableData } = walletRecordStore
const { createForm, dialogData } = storeToRefs(walletRecordStore)
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof createForm>>({
  walletId: [{ required: true, message: '请输入钱包', trigger: 'blur' }],
  description: [{ required: true, message: '请输入描述信息', trigger: 'blur' }]
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
      api.walletRecordForAdminController.create({ body: createForm.value }).then(async (res) => {
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
      <el-form-item label="钱包" prop="walletId">
        <remote-select
          placeholder="请输入用户手机号"
          :label-prop="walletLabelProp"
          :query-options="walletQueryOptions"
          v-model="createForm.walletId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="操作金额" prop="amount">
        <el-input-number v-model="createForm.amount"></el-input-number>
      </el-form-item>
      <!--      <el-form-item label="钱包当前余额" prop="balance">-->
      <!--        <el-input-number v-model="createForm.balance"></el-input-number>-->
      <!--      </el-form-item>-->
      <el-form-item label="类型" prop="type">
        <dict-select
          :dict-id="DictConstants.WALLET_RECORD_TYPE"
          v-model="createForm.type"
        ></dict-select>
      </el-form-item>
      <el-form-item label="描述信息" prop="description">
        <el-input v-model="createForm.description" type="textarea"></el-input>
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
