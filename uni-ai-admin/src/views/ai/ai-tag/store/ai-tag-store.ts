import { defineStore } from 'pinia'
import { recursiveOmit, useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type { AiTagCreateInput, AiTagSpec, AiTagUpdateInput } from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'
import type { AiModelTag } from '@/apis/__generated/model/enums'

export const aiTagQueryOptions = async (keyword: AiModelTag, id: string) => {
  const query = recursiveOmit({ name: keyword, id }) as AiTagSpec
  return (await api.aiTagForAdminController.query({ body: { query } })).content
}
export const useAiTagStore = defineStore('aiTag', () => {
  const initQuery: AiTagSpec = {}
  const initForm: AiTagCreateInput & AiTagUpdateInput = {
    factory: 'DASH_SCOPE',
    id: '',
    name: 'AIGC',
    springAiModel: ''
  }
  const tableHelper = useTableHelper(
    api.aiTagForAdminController.query,
    api.aiTagForAdminController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<AiTagSpec>(initQuery)
  const updateForm = ref<AiTagUpdateInput>({ ...initForm })
  const createForm = ref<AiTagCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
