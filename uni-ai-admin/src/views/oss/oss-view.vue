<script setup lang="ts">
import DictSelect from '@/components/dict/dict-select.vue'
import { DictConstants } from '@/apis/__generated/model/enums/DictConstants'
import { onMounted, ref } from 'vue'
import type { OSSSetting } from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ElMessage } from 'element-plus'

const form = ref<OSSSetting>({ options: {}, type: 'ALI_YUN_OSS' })
const submit = () => {
  api.osscontroller.save({ body: form.value }).then((res) => {
    ElMessage.success('保存成功')
  })
}
onMounted(() => {
  api.osscontroller.get().then((res) => {
    form.value = res
  })
})
</script>

<template>
  <el-card>
    <el-form style="width: 400px" label-position="top">
      <el-form-item label="类型">
        <dict-select :dict-id="DictConstants.OSS_TYPE" v-model="form.type"></dict-select>
      </el-form-item>
      <template v-if="form.type === 'ALI_YUN_OSS'">
        <el-form-item label="endpoint">
          <el-input v-model.trim="form.options.endpoint"></el-input>
        </el-form-item>
        <el-form-item label="bucketName">
          <el-input v-model.trim="form.options.bucketName"></el-input>
        </el-form-item>
        <el-form-item label="accessKeyId">
          <el-input v-model.trim="form.options.accessKeyId"></el-input>
        </el-form-item>
        <el-form-item label="accessKeySecret">
          <el-input v-model.trim="form.options.accessKeySecret"></el-input>
        </el-form-item>
      </template>
      <template v-if="form.type === 'TENANT_OSS'">
        <el-form-item label="endpoint">
          <el-input v-model.trim="form.options.secretId"></el-input>
        </el-form-item>
        <el-form-item label="bucketName">
          <el-input v-model.trim="form.options.secretId"></el-input>
        </el-form-item>
        <el-form-item label="accessKeyId">
          <el-input v-model.trim="form.options.region"></el-input>
        </el-form-item>
        <el-form-item label="accessKeySecret">
          <el-input v-model.trim="form.options.bucket"></el-input>
        </el-form-item>
      </template>
    </el-form>
    <el-button type="primary" @click="submit">提交</el-button>
  </el-card>
</template>

<style scoped lang="scss"></style>
