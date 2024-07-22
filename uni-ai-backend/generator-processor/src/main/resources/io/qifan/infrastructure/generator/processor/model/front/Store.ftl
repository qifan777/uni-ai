<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.Store" -->
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>

import { api } from '@/utils/api-instance'
export const ${uncapitalizeTypeName}QueryOptions = async (keyword: string, id: string) => {
  return (
          await api.${uncapitalizeTypeName}ForAdminController.query({ body: { query: { name: keyword, id } } })
  ).content
}