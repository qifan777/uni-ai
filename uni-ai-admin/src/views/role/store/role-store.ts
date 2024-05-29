import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type { RoleInput, RoleSpec } from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'

export const useRoleStore = defineStore('role', () => {
  const initQuery: RoleSpec = {}
  const initForm: RoleInput = { menuIds: [], name: '' }
  const tableHelper = useTableHelper(api.roleController.query, api.roleController, initQuery)
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<RoleSpec>(initQuery)
  const updateForm = ref<RoleInput>({ ...initForm })
  const createForm = ref<RoleInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
