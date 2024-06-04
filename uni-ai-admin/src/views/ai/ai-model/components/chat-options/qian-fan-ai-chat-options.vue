<script setup lang="ts">
const model = defineModel<{
  topK: number
  topP: number
  maxTokens: number
  temperature: number
}>({ default: {} })
</script>

<template>
  <el-form-item label="topK">
    <el-input-number v-model="model.topK" :step="0.1"></el-input-number>
  </el-form-item>
  <el-form-item label="topP">
    <el-input-number v-model="model.topP" :step="0.1"></el-input-number>
  </el-form-item>
  <el-form-item label="maxTokens">
    <el-input-number v-model="model.maxTokens"></el-input-number>
  </el-form-item>
  <el-form-item label="temperature">
    <el-input-number v-model="model.temperature"></el-input-number>
  </el-form-item>
</template>

<style scoped lang="scss"></style>
