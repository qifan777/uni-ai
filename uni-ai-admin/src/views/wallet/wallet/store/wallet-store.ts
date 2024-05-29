import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type {
  WalletCreateInput,
  WalletUpdateInput,
  WalletSpec
} from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'
import type { WalletDto } from '@/apis/__generated/model/dto'
export const walletQueryOptions = async (keyword: string, id: string) => {
  return (await api.walletForAdminController.query({ body: { query: { phone: keyword, id } } }))
    .content
}
export const walletLabelProp = (row: WalletDto['WalletRepository/COMPLEX_FETCHER_FOR_ADMIN']) => {
  if (row.user.nickname) {
    return `${row.user.nickname}(${row.user.phone})`
  } else {
    return row.user.phone
  }
}
export const useWalletStore = defineStore('wallet', () => {
  const initQuery: WalletSpec = {}
  const initForm: WalletCreateInput & WalletUpdateInput = { balance: 0, id: '', userId: '' }
  const tableHelper = useTableHelper(
    api.walletForAdminController.query,
    api.walletForAdminController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<WalletSpec>(initQuery)
  const updateForm = ref<WalletUpdateInput>({ ...initForm })
  const createForm = ref<WalletCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
