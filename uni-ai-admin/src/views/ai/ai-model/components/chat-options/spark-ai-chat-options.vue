<script setup lang="ts">
const model = defineModel<{
  domain: string
  baseUrl: string
}>({ default: {} })
</script>

<template>
  <el-form-item label="domain">
    <el-input v-model="model.domain"></el-input>
  </el-form-item>
  <el-form-item label="baseUrl">
    <el-input v-model="model.baseUrl"></el-input>
  </el-form-item>
</template>

<style scoped lang="scss"></style>
