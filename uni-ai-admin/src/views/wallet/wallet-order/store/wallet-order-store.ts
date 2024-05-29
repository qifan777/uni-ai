import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type {
  WalletOrderCreateInput,
  WalletOrderUpdateInput,
  WalletOrderSpec
} from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'
import type { WalletOrderDto } from '@/apis/__generated/model/dto'
export const walletOrderQueryOptions = async (keyword: string, id: string) => {
  return (await api.walletOrderForAdminController.query({ body: { query: { name: keyword, id } } }))
    .content
}
export const useWalletOrderStore = defineStore('walletOrder', () => {
  const initQuery: WalletOrderSpec = {}
  const initForm: WalletOrderCreateInput & WalletOrderUpdateInput = {}
  const tableHelper = useTableHelper(
    api.walletOrderForAdminController.query,
    api.walletOrderForAdminController,
    initQuery
  )
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<WalletOrderSpec>(initQuery)
  const updateForm = ref<WalletOrderUpdateInput>({ ...initForm })
  const createForm = ref<WalletOrderCreateInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
