import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type { UserCreateInput, UserSpec, UserUpdateInput } from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'

export const useUserStore = defineStore('user', () => {
  const initQuery: UserSpec = {}
  const initForm: UserCreateInput & UserUpdateInput = {
    status: 'NORMAL',
    id: '',
    roleIds: [],
    password: '',
    phone: ''
  }
  const tableHelper = useTableHelper(
    api.userForAdminController.query,
    api.userForAdminController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<UserSpec>(initQuery)
  const updateForm = ref<UserUpdateInput>({ ...initForm })
  const createForm = ref<UserCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
export const userQueryOptions = async (keyword: string, id: string) => {
  return (await api.userForAdminController.query({ body: { query: { phone: keyword, id } } }))
    .content
}
export const userLabelProp = (row: { nickname?: string; phone: string }) => {
  if (row.nickname) {
    return `${row.nickname}(${row.phone})`
  } else {
    return row.phone
  }
}
