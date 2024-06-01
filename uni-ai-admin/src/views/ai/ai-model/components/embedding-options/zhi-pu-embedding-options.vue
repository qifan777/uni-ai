<script setup lang="ts">
const model = defineModel<{ dimension: number }>({
  default: { dimension: 0 }
})
</script>
<template>
  <el-form-item label="向量维度">
    <el-input-number v-model="model.dimension"></el-input-number>
  </el-form-item>
</template>
<style scoped></style>
