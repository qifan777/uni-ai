<script setup lang="ts">
const model = defineModel<{
  apiKey: string
  apiSecret: string
  appId: string
}>({ default: {} })
</script>

<template>
  <el-form-item label="apiKey">
    <el-input v-model="model.apiKey"></el-input>
  </el-form-item>
  <el-form-item label="apiSecret">
    <el-input v-model="model.apiSecret"></el-input>
  </el-form-item>
  <el-form-item label="appId">
    <el-input v-model="model.appId"></el-input>
  </el-form-item>
</template>

<style scoped lang="scss"></style>
