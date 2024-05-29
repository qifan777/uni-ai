<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { reactive, ref, watch } from 'vue'
import { useDictStore } from '../store/dict-store'
import { assertFormValidate, assertSuccess } from '@/utils/common'
import { api } from '@/utils/api-instance'
import FooterButton from '@/components/base/dialog/footer-button.vue'
import type { FormInstance, FormRules } from 'element-plus'

const dictStore = useDictStore()
const { closeDialog, reloadTableData } = dictStore
const { createForm, dialogData } = storeToRefs(dictStore)
const createFormRef = ref<FormInstance>()
const rules = reactive<FormRules<typeof createForm>>({
  keyId: [{ required: true, message: '请输入值编号', trigger: 'blur' }],
  keyName: [{ required: true, message: '请输入值名称', trigger: 'blur' }],
  keyEnName: [{ required: true, message: '请输入值英文名称', trigger: 'blur' }],
  dictId: [{ required: true, message: '请输入字典编号', trigger: 'blur' }],
  dictName: [{ required: true, message: '请输入字典名称', trigger: 'blur' }],
  dictEnName: [{ required: true, message: '请输入字段英文名称', trigger: 'blur' }],
  orderNum: [{ required: true, message: '请输入排序号', trigger: 'blur' }]
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
      api.dictController.save({ body: createForm.value }).then(async (res) => {
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
      <el-form-item label="值编号" prop="keyId">
        <el-input-number v-model="createForm.keyId"></el-input-number>
      </el-form-item>
      <el-form-item label="值名称" prop="keyName">
        <el-input v-model="createForm.keyName"></el-input>
      </el-form-item>
      <el-form-item label="值英文名称" prop="keyEnName">
        <el-input v-model="createForm.keyEnName"></el-input>
      </el-form-item>
      <el-form-item label="字典编号" prop="dictId">
        <el-input-number v-model="createForm.dictId"></el-input-number>
      </el-form-item>
      <el-form-item label="字典名称" prop="dictName">
        <el-input v-model="createForm.dictName"></el-input>
      </el-form-item>
      <el-form-item label="字段英文名称" prop="dictEnName">
        <el-input v-model="createForm.dictEnName"></el-input>
      </el-form-item>
      <el-form-item label="排序号" prop="orderNum">
        <el-input-number v-model="createForm.orderNum"></el-input-number>
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
