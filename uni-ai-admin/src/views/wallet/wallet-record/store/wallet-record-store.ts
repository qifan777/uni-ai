import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type {
  WalletRecordCreateInput,
  WalletRecordUpdateInput,
  WalletRecordSpec
} from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'
export const useWalletRecordStore = defineStore('walletRecord', () => {
  const initQuery: WalletRecordSpec = {}
  const initForm: WalletRecordCreateInput & WalletRecordUpdateInput = {
    amount: 0,
    description: '',
    id: '',
    type: 'RECHARGE',
    walletId: ''
  }
  const tableHelper = useTableHelper(
    api.walletRecordForAdminController.query,
    api.walletRecordForAdminController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<WalletRecordSpec>(initQuery)
  const updateForm = ref<WalletRecordUpdateInput>({ ...initForm })
  const createForm = ref<WalletRecordCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
