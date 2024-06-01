<script setup lang="ts">
const model = defineModel<{ accessKey: string; secretKey: string }>({
  default: { accessKey: '', secretKey: '' }
})
</script>

<template>
  <el-form-item label="accessKey">
    <el-input v-model="model.accessKey"></el-input>
  </el-form-item>
  <el-form-item label="secretKey">
    <el-input v-model="model.secretKey"></el-input>
  </el-form-item>
</template>

<style scoped lang="scss"></style>
