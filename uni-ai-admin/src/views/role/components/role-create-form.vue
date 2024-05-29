<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { onMounted, reactive, ref, watch } from 'vue'
import { useRoleStore } from '../store/role-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { MenuTreeDto } from '@/typings'
import { buildMenuTree } from '@/views/menu/store/menu-store'
import MenuTree from '@/views/menu/components/menu-tree.vue'

const roleStore = useRoleStore()
const { closeDialog, reloadTableData } = roleStore
const { createForm, dialogData } = storeToRefs(roleStore)
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof createForm>>({
  name: [{ required: true, message: '请输入角色名称', trigger: 'blur' }]
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
      api.roleController.save({ body: createForm.value }).then(async (res) => {
        assertSuccess(res).then(() => {
          closeDialog()
          reloadTableData()
        })
      })
    )
  )
}
const menuTreeList = ref<MenuTreeDto[]>([])
onMounted(() => {
  api.menuController.query({ body: { pageNum: 1, query: {}, pageSize: 100000 } }).then((res) => {
    menuTreeList.value = buildMenuTree(null, res.content)
    console.log(menuTreeList.value)
  })
})
const handleNodeCheckChange = (
  value1: MenuTreeDto,
  value2: { checkedNodes: MenuTreeDto[]; checkedKeys: string[] }
) => {
  createForm.value.menuIds = value2.checkedKeys
}
</script>
<template>
  <div class="create-form">
    <el-form labelWidth="120" class="form" ref="createFormRef" :model="createForm" :rules="rules">
      <el-form-item label="角色名称" prop="name">
        <el-input v-model="createForm.name"></el-input>
      </el-form-item>
      <el-form-item label="菜单">
        <menu-tree
          :menu-tree="menuTreeList"
          show-checkbox
          check-strictly
          nodeKey="id"
          @check="handleNodeCheckChange"
        >
          <template v-slot:default="{ node }">{{ node.label }} </template>
        </menu-tree>
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
