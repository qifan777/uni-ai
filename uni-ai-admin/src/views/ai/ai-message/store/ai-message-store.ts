import {defineStore} from 'pinia'
import {useTableHelper} from '@/components/base/table/table-helper'
import {useDialogHelper} from '@/components/base/dialog/dialog-helper'
import {useQueryHelper} from '@/components/base/query/query-helper'
import type {AiMessageCreateInput, AiMessageSpec, AiMessageUpdateInput} from '@/apis/__generated/model/static'
import {api} from '@/utils/api-instance'
import {ref} from 'vue'

export const useAiMessageStore = defineStore('aiMessage', () => {
  const initQuery: AiMessageSpec = {}
  const initForm: AiMessageCreateInput & AiMessageUpdateInput = {
    aiSessionId: '',
    content: '',
    id: '',
    type: 'USER'
  }
  const tableHelper = useTableHelper(
    api.aiMessageForAdminController.query,
    api.aiMessageForAdminController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<AiMessageSpec>(initQuery)
  const updateForm = ref<AiMessageUpdateInput>({ ...initForm })
  const createForm = ref<AiMessageCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
