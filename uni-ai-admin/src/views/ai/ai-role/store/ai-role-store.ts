import {defineStore} from 'pinia'
import {useTableHelper} from '@/components/base/table/table-helper'
import {useDialogHelper} from '@/components/base/dialog/dialog-helper'
import {useQueryHelper} from '@/components/base/query/query-helper'
import type {AiRoleCreateInput, AiRoleSpec, AiRoleUpdateInput} from '@/apis/__generated/model/static'
import {api} from '@/utils/api-instance'
import {ref} from 'vue'

export const aiRoleQueryOptions = async (keyword: string, id: string) => {
  return (await api.aiRoleForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
export const useAiRoleStore = defineStore('aiRole', () => {
  const initQuery: AiRoleSpec = {}
  const initForm: AiRoleCreateInput & AiRoleUpdateInput = {
    description: '',
    id: '',
    name: '',
    prompts: []
  }
  const tableHelper = useTableHelper(
    api.aiRoleForAdminController.query,
    api.aiRoleForAdminController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<AiRoleSpec>(initQuery)
  const updateForm = ref<AiRoleUpdateInput>({ ...initForm })
  const createForm = ref<AiRoleCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
