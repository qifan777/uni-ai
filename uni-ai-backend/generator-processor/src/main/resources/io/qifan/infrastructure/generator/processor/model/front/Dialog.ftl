<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.Dialog" -->
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>
<script lang="ts" setup>
  import { type Component } from 'vue'
  import { storeToRefs } from 'pinia'
  import { use${entityType.typeName}Store } from '../store/${entityType.toFrontNameCase()}-store'
  import ${entityType.typeName}CreateForm from './${entityType.toFrontNameCase()}-create-form.vue'
  import ${entityType.typeName}UpdateForm from './${entityType.toFrontNameCase()}-update-form.vue'
  import type { EditMode } from '@/typings'
  const ${uncapitalizeTypeName}Store = use${entityType.typeName}Store()
  const { dialogData } = storeToRefs(${uncapitalizeTypeName}Store)

  const formMap: Record<EditMode, Component> = {
    CREATE: ${entityType.typeName}CreateForm,
    UPDATE: ${entityType.typeName}UpdateForm
  }
</script>
<template>
  <div>
    <el-dialog v-model="dialogData.visible" :title="dialogData.title" :width="dialogData.width">
      <component :is="formMap[dialogData.mode]"></component>
    </el-dialog>
  </div>
</template>

<style lang="scss" scoped></style>