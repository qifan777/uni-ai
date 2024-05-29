<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useMenuStore } from '../store/menu-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import DictSelect from '@/components/dict/dict-select.vue'
import type { FormInstance, FormRules } from 'element-plus'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import RemoteSelect from '@/components/base/form/remote-select.vue'

const menuStore = useMenuStore()
const { closeDialog, reloadTableData } = menuStore
const { updateForm, dialogData } = storeToRefs(menuStore)
const updateFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof updateForm>>({
  name: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  path: [{ required: true, message: '请输入路由路径', trigger: 'blur' }],
  menuType: [{ required: true, message: '请输入菜单类型', trigger: 'change' }]
})
const init = async () => {
  dialogData.value.title = '编辑'
  updateForm.value = {
    ...(await api.menuController.findById({ id: updateForm.value.id || '' }))
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
  if (updateForm.value.parentId === '') {
    updateForm.value.parentId = undefined
  }
  updateFormRef.value?.validate(
    assertFormValidate(() => {
      api.menuController.save({ body: updateForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          closeDialog()
          reloadTableData()
        })
      })
    })
  )
}
const menuQueryOptions = async (keyword: string, parentId: string) => {
  return (
    await api.menuController.query({
      body: { query: { name: keyword, menuType: 'DIRECTORY', id: parentId } }
    })
  ).content
}
</script>
<template>
  <div class="update-form">
    <el-form labelWidth="120" class="form" ref="updateFormRef" :model="updateForm" :rules="rules">
      <el-form-item label="菜单名称" prop="name">
        <el-input v-model="updateForm.name"></el-input>
      </el-form-item>
      <el-form-item label="父菜单Id" prop="parentId">
        <remote-select
          :key="updateForm.id"
          label-prop="name"
          :query-options="menuQueryOptions"
          v-model="updateForm.parentId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="路由路径" prop="path">
        <el-input v-model="updateForm.path"></el-input>
      </el-form-item>
      <el-form-item label="排序号" prop="orderNum">
        <el-input-number v-model="updateForm.orderNum"></el-input-number>
      </el-form-item>
      <el-form-item label="菜单类型" prop="menuType">
        <dict-select :dict-id="DictConstants.MENU_TYPE" v-model="updateForm.menuType"></dict-select>
      </el-form-item>
      <el-form-item label="图标" prop="icon">
        <el-input v-model.trim="updateForm.icon"></el-input>
      </el-form-item>
      <el-form-item label="是否可见" prop="visible">
        <el-switch v-model="updateForm.visible"></el-switch>
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
