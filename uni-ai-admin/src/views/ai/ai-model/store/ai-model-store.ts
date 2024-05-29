import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type {
  AiModelCreateInput,
  AiModelSpec,
  AiModelUpdateInput
} from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'

export const aiModelQueryOptions = async (keyword: string, id: string) => {
  return (await api.aiModelForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
export const useAiModelStore = defineStore('aiModel', () => {
  const initQuery: AiModelSpec = {}
  const initForm: AiModelCreateInput & AiModelUpdateInput = {
    factory: 'OPEN_AI',
    id: '',
    name: '',
    options: {},
    tagIds: []
  }
  const tableHelper = useTableHelper(
    api.aiModelForAdminController.query,
    api.aiModelForAdminController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<AiModelSpec>(initQuery)
  const updateForm = ref<AiModelUpdateInput>({ ...initForm })
  const createForm = ref<AiModelCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
