<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.View" -->
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>
<script lang="ts" setup>
    import ${entityType.typeName}Table from './components/${entityType.toFrontNameCase()}-table.vue'
    import ${entityType.typeName}Query from './components/${entityType.toFrontNameCase()}-query.vue'
    import { useTableHelper } from '@/components/base/table/table-helper'
    import { api } from '@/utils/api-instance'
    import { provide } from 'vue'
    import type { ${entityType.typeName}Spec } from '@/apis/__generated/model/static'
    import { useQueryHelper } from '@/components/base/query/query-helper'
    const initQuery: ${entityType.typeName}Spec = {}
    const tableHelper = useTableHelper(
        api.${uncapitalizeTypeName}ForAdminController.query,
        api.${uncapitalizeTypeName}ForAdminController,
        initQuery
    )
    const { query, restQuery } = useQueryHelper<${entityType.typeName}Spec>(initQuery)
    provide('${uncapitalizeTypeName}TableHelper', tableHelper)
</script>
<template>
    <div class="${entityType.toFrontNameCase()}-view">
        <${entityType.toFrontNameCase()}-query
                v-model:query="query"
                @reset="restQuery"
                @search="tableHelper.reloadTableData({ query })"
        ></${entityType.toFrontNameCase()}-query>
        <${entityType.toFrontNameCase()}-table></${entityType.toFrontNameCase()}-table>
    </div>
</template>

<style lang="scss" scoped>
  .${entityType.toFrontNameCase()}-view {
    background: white;
    padding: 20px;
    border-radius: 5px;
  }
</style>
