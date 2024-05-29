<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.Store" -->
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>

import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type { ${entityType.typeName}CreateInput,${entityType.typeName}UpdateInput, ${entityType.typeName}Spec } from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'
import type { ${entityType.typeName}Dto } from '@/apis/__generated/model/dto'
export const ${uncapitalizeTypeName}QueryOptions = async (keyword: string, id: string) => {
  return (
          await api.${uncapitalizeTypeName}ForAdminController.query({ body: { query: { name: keyword, id } } })
  ).content
}
export const use${entityType.typeName}Store = defineStore('${uncapitalizeTypeName}', () => {
  const initQuery: ${entityType.typeName}Spec = {}
  const initForm: ${entityType.typeName}CreateInput & ${entityType.typeName}UpdateInput = { }
  const tableHelper = useTableHelper(api.${uncapitalizeTypeName}ForAdminController.query, api.${uncapitalizeTypeName}ForAdminController, initQuery)
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<${entityType.typeName}Spec>(initQuery)
  const updateForm =ref<${entityType.typeName}UpdateInput>({ ...initForm })
  const createForm = ref<${entityType.typeName}CreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm,initForm}
})
