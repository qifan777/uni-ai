import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type {
  AiPluginCreateInput,
  AiPluginSpec,
  AiPluginUpdateInput
} from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'

export const aiPluginQueryOptions = async (keyword: string, names: string[]) => {
  return (await api.aiPluginForAdminController.query({ body: { query: { name: keyword, names } } }))
    .content
}
export const useAiPluginStore = defineStore('aiPlugin', () => {
  const initQuery: AiPluginSpec = {}
  const initForm: AiPluginCreateInput & AiPluginUpdateInput = {
    description: '',
    id: '',
    name: '',
    parameters: { type: '', properties: {}, required: [] }
  }
  const tableHelper = useTableHelper(
    api.aiPluginForAdminController.query,
    api.aiPluginForAdminController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<AiPluginSpec>(initQuery)
  const updateForm = ref<AiPluginUpdateInput>({ ...initForm })
  const createForm = ref<AiPluginCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
