<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.Query" -->
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>
<script lang="ts" setup>
  import RemoteSelect from '@/components/base/form/remote-select.vue'
  import type { ${entityType.typeName}Spec } from '@/apis/__generated/model/static'
  const emit = defineEmits<{ search: [value: ${entityType.typeName}Spec]; rest: [] }>()
  const query = defineModel<${entityType.typeName}Spec>('query', { required: true })
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <#list getQueryItems() as itemField>
        <@includeModel object=itemField></@includeModel>
      </#list>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button type="primary" size="small" @click="() => emit('search', query)">
            查询
          </el-button>
          <el-button type="warning" size="small" @click="() => emit('rest')"> 重置</el-button>
        </div>
      </el-form-item>
    </el-form>
  </div>
</template>

<style lang="scss" scoped>
  :deep(.el-form-item) {
    margin-bottom: 5px;
  }

  .search {
    display: flex;
    flex-flow: column nowrap;
    width: 100%;

    .btn-wrapper {
      margin-left: 20px;
    }
  }
</style>

