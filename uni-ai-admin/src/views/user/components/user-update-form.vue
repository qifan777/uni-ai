<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useUserStore } from '../store/user-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import ImageUpload from '@/components/image/image-upload.vue'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import DictSelect from '@/components/dict/dict-select.vue'
import type { FormInstance, FormRules } from 'element-plus'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import RemoteSelect from '@/components/base/form/remote-select.vue'

const userStore = useUserStore()
const { closeDialog, reloadTableData } = userStore
const { updateForm, dialogData } = storeToRefs(userStore)
const updateFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof updateForm>>({
  phone: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
})
const init = async () => {
  dialogData.value.title = '编辑'
  const res = await api.userForAdminController.findById({ id: updateForm.value.id || '' })
  updateForm.value = { ...res, roleIds: res.rolesView.map((role) => role.id) }
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
      api.userForAdminController.update({ body: updateForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          closeDialog()
          reloadTableData()
        })
      })
    })
  )
}
const roleQueryOptions = async (keyword: string, roleIds: string[]) => {
  return (await api.roleController.query({ body: { query: { name: keyword, ids: roleIds } } }))
    .content
}
</script>
<template>
  <div class="update-form">
    <el-form labelWidth="120" class="form" ref="updateFormRef" :model="updateForm" :rules="rules">
      <el-form-item label="手机号" prop="phone">
        <el-input v-model="updateForm.phone"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="updateForm.password"></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="updateForm.nickname"></el-input>
      </el-form-item>
      <el-form-item label="头像" prop="avatar">
        <image-upload v-model="updateForm.avatar"></image-upload>
      </el-form-item>
      <el-form-item label="性别" prop="gender">
        <dict-select :dict-id="DictConstants.GENDER" v-model="updateForm.gender"></dict-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <dict-select :dict-id="DictConstants.USER_STATUS" v-model="updateForm.status"></dict-select>
      </el-form-item>
      <el-form-item label="角色">
        <remote-select
          :key="updateForm.id"
          :query-options="roleQueryOptions"
          v-model="updateForm.roleIds"
          label-prop="name"
          multiple
        >
        </remote-select>
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
