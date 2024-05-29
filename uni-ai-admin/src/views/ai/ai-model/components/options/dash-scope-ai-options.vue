<script setup lang="ts">
const model = defineModel<{ [key: string]: any }>({
  default: {
    maxTokens: 2048,
    topP: 0
  }
})
</script>

<template>
  <el-form-item label="maxTokens">
    <el-input-number v-model="model.maxTokens"></el-input-number>
  </el-form-item>
  <el-form-item label="top">
    <el-input-number v-model="model.topP" :step="0.1"></el-input-number>
  </el-form-item>
</template>

<style scoped lang="scss"></style>
