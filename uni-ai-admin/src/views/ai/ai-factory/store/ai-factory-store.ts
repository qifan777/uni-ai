import {defineStore} from 'pinia'
import {useTableHelper} from '@/components/base/table/table-helper'
import {useDialogHelper} from '@/components/base/dialog/dialog-helper'
import {useQueryHelper} from '@/components/base/query/query-helper'
import type {AiFactoryCreateInput, AiFactorySpec, AiFactoryUpdateInput} from '@/apis/__generated/model/static'
import {api} from '@/utils/api-instance'
import {ref} from 'vue'

export const aiFactoryQueryOptions = async (_keyword: string, id: string) => {
  return (await api.aiFactoryForFrontController.query({ body: { query: { id } } })).content
}
export const useAiFactoryStore = defineStore('aiFactory', () => {
  const initQuery: AiFactorySpec = {}
  const initForm: AiFactoryCreateInput & AiFactoryUpdateInput = {
    id: '',
    name: 'DASH_SCOPE',
    options: {}
  }
  const tableHelper = useTableHelper(
    api.aiFactoryForFrontController.query,
    api.aiFactoryForFrontController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<AiFactorySpec>(initQuery)
  const updateForm = ref<AiFactoryUpdateInput>({ ...initForm })
  const createForm = ref<AiFactoryCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
