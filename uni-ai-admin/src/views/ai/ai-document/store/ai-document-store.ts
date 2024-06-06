import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type {
  AiDocumentCreateInput,
  AiDocumentSpec,
  AiDocumentUpdateInput
} from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'

export const aiDocumentQueryOptions = async (keyword: string, id: string) => {
  return (await api.aiDocumentForFrontController.query({ body: { query: { name: keyword, id } } }))
    .content
}
export const useAiDocumentStore = defineStore('aiDocument', () => {
  const initQuery: AiDocumentSpec = {}
  const initForm: AiDocumentCreateInput & AiDocumentUpdateInput = {
    aiCollectionId: '',
    content: '',
    id: '',
    name: ''
  }
  const tableHelper = useTableHelper(
    api.aiDocumentForFrontController.query,
    api.aiDocumentForFrontController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<AiDocumentSpec>(initQuery)
  const updateForm = ref<AiDocumentUpdateInput>({ ...initForm })
  const createForm = ref<AiDocumentCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
