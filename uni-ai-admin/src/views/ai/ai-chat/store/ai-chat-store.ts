import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { AiMessageDto, AiSessionDto } from '@/apis/__generated/model/dto'
import { api } from '@/utils/api-instance'
import type { AiSessionCreateInput } from '@/apis/__generated/model/static'

type AiSession = AiSessionDto['AiSessionRepository/COMPLEX_FETCHER_FOR_FRONT'] & {
  checked: boolean
}
export type MessageWithOptions = { content: AiMessage['content']; options: { knowledge: boolean } }

export type AiMessage = Pick<
  AiMessageDto['AiMessageRepository/COMPLEX_FETCHER_FOR_FRONT'],
  'type' | 'content' | 'createdTime'
>
export const useAiChatStore = defineStore('ai-chat', () => {
  const isEdit = ref(false)
  const activeSession = ref<AiSession>()
  const sessionList = ref<AiSession[]>([])
  const messageList = ref<AiMessage[]>([])
  const handleCreateSession = async (session: AiSessionCreateInput) => {
    console.log(session)
    const res = await api.aiSessionForFrontController.create({ body: session })
    const sessionRes = await api.aiSessionForFrontController.findById({ id: res })
    sessionList.value.unshift({ ...sessionRes, checked: false })
    handleSessionSwitch(sessionList.value[0])
  } // 切换会话
  const handleSessionSwitch = (session: AiSession) => {
    console.log(session)
    activeSession.value = session
    messageList.value = session.messages
  }
  // 从会话列表中删除会话
  const handleDeleteSession = (session: AiSession) => {
    api.aiSessionForFrontController.delete({ body: [session.id] }).then(() => {
      const index = sessionList.value.findIndex((value) => {
        return value.id === session.id
      })
      sessionList.value.splice(index, 1)
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
    messageList,
    handleUpdateSession,
    handleCreateSession,
    handleDeleteSession,
    handleSessionSwitch
  }
})
