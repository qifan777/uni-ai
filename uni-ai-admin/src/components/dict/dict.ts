import type { DictSpec, Page } from '@/apis/__generated/model/static'
import type { DictDto } from '@/apis/__generated/model/dto'
import { api } from '@/utils/api-instance'

const dictMap: Record<number, Promise<Page<DictDto['DictRepository/COMPLEX_FETCHER']>>> = {}
export const queryDict = (dictSpec: DictSpec) => {
  if (!dictSpec.dictId) return
  let res = dictMap[dictSpec.dictId]
  if (res) return res
  res = api.dictController.query({
    body: {
      pageNum: 1,
      pageSize: 1000,
      likeMode: 'ANYWHERE',
      query: dictSpec,
      sorts: [{ property: 'dictId', direction: 'ASC' }]
    }
  })
  dictMap[dictSpec.dictId] = res
  return res
}
