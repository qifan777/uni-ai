<script setup lang="ts">
import { ref } from 'vue'
import type { Setting } from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ElMessage } from 'element-plus'

const form = ref<Setting>({ ocrPrice: 0, tokenPrice: 0 })
const handleSubmit = () => {
  api.settingController.saveSetting({ body: form.value }).then((res) => {
    ElMessage.success('保存成功')
  })
}
api.settingController.get().then((res) => {
  form.value = res
})
</script>

<template>
  <div>
    <el-card>
      <el-form size="small" label-width="160">
        <el-form-item label="token单价（元/1k token）">
          <el-input-number v-model="form.tokenPrice" :min="0.00000001" :max="1000" :precision="8" />
        </el-form-item>
        <el-form-item label="OCR单价（元/1次）">
          <el-input-number v-model="form.ocrPrice" :min="0.00000001" :max="1000" :precision="8" />
        </el-form-item>
        <el-button type="primary" @click="handleSubmit"> 提交</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<style scoped lang="scss"></style>
