import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type {
  WalletItemCreateInput,
  WalletItemUpdateInput,
  WalletItemSpec
} from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'
export const walletItemQueryOptions = async (keyword: string, id: string) => {
  return (await api.walletItemForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
export const useWalletItemStore = defineStore('walletItem', () => {
  const initQuery: WalletItemSpec = {}
  const initForm: WalletItemCreateInput & WalletItemUpdateInput = {
    amount: 0,
    id: '',
    name: '',
    price: 0
  }
  const tableHelper = useTableHelper(
    api.walletItemForAdminController.query,
    api.walletItemForAdminController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<WalletItemSpec>(initQuery)
  const updateForm = ref<WalletItemUpdateInput>({ ...initForm })
  const createForm = ref<WalletItemCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
