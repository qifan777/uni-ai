<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.Query" -->
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>
<script lang="ts" setup>
  import { toRefs } from 'vue'
  import { use${entityType.typeName}Store } from '../store/${entityType.toFrontNameCase()}-store'
  import { storeToRefs } from 'pinia'
  import ${'DictSelect'} from '@/components/dict/dict-select.vue'
  import ${'DatetimePicker'} from '@/components/datetime/datetime-picker.vue'
  import { ${'DictConstants'} } from '@/apis/__generated/model/enums/DictConstants'
  import { ${'userLabelProp'}, ${'userQueryOptions'} } from '@/views/user/store/user-store'
  import ${'RemoteSelect'} from '@/components/base/form/remote-select.vue'
  const ${uncapitalizeTypeName}Store = use${entityType.typeName}Store()
  const { queryData } = storeToRefs(${uncapitalizeTypeName}Store)
  const { query } = toRefs(queryData.value)
</script>
<template>
  <div class="search">
    <el-form inline label-width="80" size="small">
      <#list getQueryItems() as itemField>
        <@includeModel object=itemField></@includeModel>
      </#list>
      <el-form-item label=" ">
        <div class="btn-wrapper">
          <el-button
                  type="primary"
                  size="small"
                  @click="${uncapitalizeTypeName}Store.reloadTableData({ query: query, likeMode: 'ANYWHERE' })"
          >
            查询
          </el-button>
          <el-button type="warning" size="small" @click="${uncapitalizeTypeName}Store.restQuery"> 重置</el-button>
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

