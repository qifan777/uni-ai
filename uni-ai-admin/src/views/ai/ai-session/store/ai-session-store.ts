import {defineStore} from 'pinia'
import {useTableHelper} from '@/components/base/table/table-helper'
import {useDialogHelper} from '@/components/base/dialog/dialog-helper'
import {useQueryHelper} from '@/components/base/query/query-helper'
import type {AiSessionCreateInput, AiSessionSpec, AiSessionUpdateInput} from '@/apis/__generated/model/static'
import {api} from '@/utils/api-instance'
import {ref} from 'vue'

export const aiSessionQueryOptions = async (keyword: string, id: string) => {
  return (await api.aiSessionForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
export const useAiSessionStore = defineStore('aiSession', () => {
  const initQuery: AiSessionSpec = {}
  const initForm: AiSessionCreateInput & AiSessionUpdateInput = { id: '', name: '' }
  const tableHelper = useTableHelper(
    api.aiSessionForAdminController.query,
    api.aiSessionForAdminController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<AiSessionSpec>(initQuery)
  const updateForm = ref<AiSessionUpdateInput>({ ...initForm })
  const createForm = ref<AiSessionCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
