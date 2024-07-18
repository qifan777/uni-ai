<script setup lang="ts">
const model = defineModel<{ apiKey: string; secretKey: string }>({
  default: { apiKey: '', secretKey: '' }
})
</script>

<template>
  <el-form-item label="apiKey">
    <el-input v-model="model.apiKey"></el-input>
  </el-form-item>
  <el-form-item label="secretKey">
    <el-input v-model="model.secretKey"></el-input>
  </el-form-item>
</template>

<style scoped lang="scss"></style>
