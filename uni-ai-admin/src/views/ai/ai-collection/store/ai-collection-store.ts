import {defineStore} from 'pinia'
import {useTableHelper} from '@/components/base/table/table-helper'
import {useDialogHelper} from '@/components/base/dialog/dialog-helper'
import {useQueryHelper} from '@/components/base/query/query-helper'
import type {AiCollectionCreateInput, AiCollectionSpec, AiCollectionUpdateInput} from '@/apis/__generated/model/static'
import {api} from '@/utils/api-instance'
import {ref} from 'vue'

export const aiCollectionQueryOptions = async (keyword: string, id: string) => {
  return (
    await api.aiCollectionForFrontController.query({ body: { query: { name: keyword, id } } })
  ).content
}
export const useAiCollectionStore = defineStore('aiCollection', () => {
  const initQuery: AiCollectionSpec = {}
  const initForm: AiCollectionCreateInput & AiCollectionUpdateInput = {
    collectionName: '',
    embeddingModelId: '',
    id: '',
    name: ''
  }
  const tableHelper = useTableHelper(
    api.aiCollectionForFrontController.query,
    api.aiCollectionForFrontController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<AiCollectionSpec>(initQuery)
  const updateForm = ref<AiCollectionUpdateInput>({ ...initForm })
  const createForm = ref<AiCollectionCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
