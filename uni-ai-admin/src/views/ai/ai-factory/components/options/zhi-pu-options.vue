<script setup lang="ts">
const model = defineModel<{ apiKey: string; baseUrl: string }>({
  default: { apiKey: '', baseUrl: '' }
})
</script>

<template>
  <el-form-item label="apiKey">
    <el-input v-model="model.apiKey"></el-input>
  </el-form-item>
</template>

<style scoped lang="scss"></style>
