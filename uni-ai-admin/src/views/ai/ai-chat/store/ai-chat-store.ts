import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { AiMessageDto, AiModelDto, AiSessionDto } from '@/apis/__generated/model/dto'
import { api } from '@/utils/api-instance'
import type { AiSessionCreateInput, ChatParams } from '@/apis/__generated/model/static'
export type ChatParamsExt = ChatParams & {
  aiModel?: AiModelDto['AiModelRepository/COMPLEX_FETCHER_FOR_ADMIN']
}
export type AiSession = Pick<
  AiSessionDto['AiSessionRepository/COMPLEX_FETCHER_FOR_FRONT'],
  'id' | 'name' | 'editedTime'
> & {
  messages: AiMessage[]
  checked: boolean
}

export type AiMessage = Pick<
  AiMessageDto['AiMessageRepository/COMPLEX_FETCHER_FOR_ADMIN'],
  'id' | 'type' | 'content' | 'createdTime' | 'aiSessionId'
>
export const useAiChatStore = defineStore('ai-chat', () => {
  const isEdit = ref(false)
  const activeSession = ref<AiSession>()
  const sessionList = ref<AiSession[]>([])
  const handleCreateSession = async (session: AiSessionCreateInput) => {
    console.log(session)
    const res = await api.aiSessionForFrontController.create({ body: session })
    const sessionRes = await api.aiSessionForFrontController.findById({ id: res })
    sessionList.value.unshift({ ...sessionRes, checked: false })
    activeSession.value = sessionList.value[0]
  }
  // 从会话列表中删除会话
  const handleDeleteSession = (session: AiSession) => {
    api.aiSessionForFrontController.delete({ body: [session.id] }).then(() => {
      const index = sessionList.value.findIndex((value) => {
        return value.id === session.id
      })
      sessionList.value.splice(index, 1)
      if (index == sessionList.value.length) {
        activeSession.value = sessionList.value[index - 1]
      } else {
        activeSession.value = sessionList.value[index]
      }
    })
  }
  // 新增会话
  const handleUpdateSession = () => {
    if (!activeSession.value) {
      return
    }
    api.aiSessionForFrontController.update({
      body: { ...activeSession.value }
    })
    isEdit.value = false
  }

  return {
    isEdit,
    activeSession,
    sessionList,
    handleUpdateSession,
    handleCreateSession,
    handleDeleteSession
  }
})
