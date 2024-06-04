<script setup lang="ts">
const model = defineModel<{ width: number; height: number; n: number }>({
  default: {}
})
</script>
<template>
  <el-form-item label="宽度">
    <el-input-number v-model="model.width" controls-position="right"></el-input-number>
  </el-form-item>
  <el-form-item label="高度">
    <el-input-number v-model="model.height" controls-position="right"></el-input-number>
  </el-form-item>
  <el-form-item label="张数">
    <el-input-number v-model="model.n" :max="4" controls-position="right"></el-input-number>
  </el-form-item>
</template>
<style scoped></style>
