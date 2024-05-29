import { defineStore } from 'pinia'
import { useTableHelper } from '@/components/base/table/table-helper'
import { useDialogHelper } from '@/components/base/dialog/dialog-helper'
import { useQueryHelper } from '@/components/base/query/query-helper'
import type { DictInput, DictSpec } from '@/apis/__generated/model/static'
import { api } from '@/utils/api-instance'
import { ref } from 'vue'

export const useDictStore = defineStore('dict', () => {
  const initQuery: DictSpec = {}
  const initForm: DictInput = {
    dictEnName: '',
    dictId: 0,
    dictName: '',
    keyEnName: '',
    keyId: 0,
    keyName: '',
    orderNum: 0
  }
  const tableHelper = useTableHelper(api.dictController.query, api.dictController, initQuery)
  const dialogHelper = useDialogHelper()
  const queryHelper = useQueryHelper<DictSpec>(initQuery)
  const updateForm = ref<DictInput>({ ...initForm })
  const createForm = ref<DictInput>({ ...initForm })
  return { ...tableHelper, ...dialogHelper, ...queryHelper, updateForm, createForm, initForm }
})
