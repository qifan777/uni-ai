import {defineStore} from 'pinia'
import {useTableHelper} from '@/components/base/table/table-helper'
import {useDialogHelper} from '@/components/base/dialog/dialog-helper'
import {useQueryHelper} from '@/components/base/query/query-helper'
import type {AiTagCreateInput, AiTagSpec, AiTagUpdateInput} from '@/apis/__generated/model/static'
import {api} from '@/utils/api-instance'
import {ref} from 'vue'

export const aiTagQueryOptions = async (keyword: string, id: string) => {
  return (await api.aiTagForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
export const useAiTagStore = defineStore('aiTag', () => {
  const initQuery: AiTagSpec = {}
  const initForm: AiTagCreateInput & AiTagUpdateInput = {}
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
