<script lang="ts" setup>
import { computed, nextTick, onMounted, provide, ref } from 'vue'
import SessionItem from './components/session-item.vue'
import { Close, Delete, EditPen } from '@element-plus/icons-vue'
import MessageRow from './components/message-row.vue'
import MessageInput from './components/message-input.vue'

import dayjs from 'dayjs'
import { storeToRefs } from 'pinia'
import { ElIcon } from 'element-plus'
import { api } from '@/utils/api-instance'
import { useHomeStore } from '@/stores/home-store'
import SessionCreateDialog from './components/session-create-dialog.vue'
import { SSE, type SSEvent } from 'sse.js'
import { type AiMessage, type MessageWithOptions, useAiChatStore } from './store/ai-chat-store'
import type { AiModelTag } from '@/apis/__generated/model/enums'
import _ from 'lodash'
import { useTagStore } from '@/layout/store/tag-store'
import type { ChatMessageRequest } from '@/apis/__generated/model/static'
import ImageOptions from '@/views/ai/ai-model/components/image-options/image-options.vue'
import ChatOptions from '@/views/ai/ai-model/components/chat-options/chat-options.vue'

type ChatResponse = {
  metadata: {
    usage: {
      totalTokens: number
    }
  }
  result: {
    metadata: {
      finishReason: string
    }
    output: {
      messageType: string
      content: string
    }
  }
}

const homeStore = useHomeStore()
const chatStore = useAiChatStore()
const { handleDeleteSession, handleUpdateSession } = chatStore
const { activeSession, sessionList, isEdit } = storeToRefs(chatStore)
const messageListRef = ref<InstanceType<typeof HTMLDivElement>>()
const loading = ref(true)
const activeTag = computed<AiModelTag>(() => {
  if (!activeSession.value) {
    return 'AIGC'
  }
  const model = activeSession.value.aiModel
  if (!model) {
    return 'AIGC'
  }
  if (model.tagsView.filter((tag) => tag.name == 'VISION').length > 0) {
    return 'VISION'
  }
  if (model.tagsView.filter((tag) => tag.name == 'IMAGE').length > 0) {
    return 'IMAGE'
  }
  return 'AIGC'
})
provide('activeTag', activeTag)

onMounted(async () => {
  useTagStore().collapse = true
  const userInfo = await homeStore.getUserInfo()
  // 查询自己的聊天会话
  api.aiSessionForFrontController
    .query({ body: { query: { creatorId: userInfo.id } } })
    .then((res) => {
      // 讲会话添加到列表中
      sessionList.value = res.content.map((row) => {
        return { ...row, checked: false }
      })
      // 默认选中的聊天会话是第一个
      if (sessionList.value.length > 0) {
        activeSession.value = sessionList.value[0]
      }
      loading.value = false
    })
})

// ChatGPT的回复
const responseMessage = ref<AiMessage>({
  id: '',
  type: 'ASSISTANT',
  content: [],
  aiSessionId: '',
  // 因为回复的消息没有id，所以统一将创建时间+index当作key
  createdTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
})
const handleChatMessage = (message: MessageWithOptions) => {
  if (!activeSession.value) return
  // 用户的提问
  const chatMessage = {
    id: '',
    aiSessionId: activeSession.value.id,
    content: message.content,
    type: 'USER',
    createdTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
  } satisfies AiMessage

  // 新建一个ChatGPT回复对象，不能重复使用同一个对象。
  responseMessage.value = {
    id: '',
    type: 'ASSISTANT',
    content: [{ text: '' }],
    aiSessionId: activeSession.value.id,
    // 因为回复的消息没有id，所以统一将创建时间+index当作key
    createdTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
  }
  const body: ChatMessageRequest = {
    message: chatMessage,
    chatParams: { tag: activeTag.value, ...message.options },
    chatOptions: activeSession.value.aiModel.options || {},
    imageOptions: {}
  }
  const evtSource = new SSE(import.meta.env.VITE_API_PREFIX + '/front/ai-message/chat', {
    withCredentials: true,
    start: false,
    headers: { 'Content-Type': 'application/json' },
    payload: JSON.stringify(body),
    method: 'POST'
  })
  evtSource.addEventListener('chat', async (event: any) => {
    console.log('status', event)
    const response = JSON.parse(event.data) as ChatResponse
    const finishReason = response.result.metadata.finishReason
    if (response.result.output.content) {
      responseMessage.value.content[0].text += response.result.output.content
      nextTick(() => {
        messageListRef.value?.scrollTo(0, messageListRef.value.scrollHeight)
      })
    }
    if (finishReason && finishReason.toLowerCase() == 'stop') {
      evtSource.close()
      await api.aiMessageForFrontController.create({ body: chatMessage })
      await api.aiMessageForFrontController.create({ body: responseMessage.value })
    }
  })
  evtSource.stream()
  evtSource.onerror = (error: SSEvent) => {
    console.log(error)
  }

  // 将两条消息显示在页面中
  activeSession.value.messages.push(...[chatMessage, responseMessage.value])
  nextTick(() => {
    messageListRef.value?.scrollTo(0, messageListRef.value.scrollHeight)
  })
}
const handleSendMessage = (message: MessageWithOptions) => {
  if (activeTag.value == 'IMAGE') {
    handleImageMessage(message)
  } else {
    handleChatMessage(message)
  }
}
const handleImageMessage = (message: MessageWithOptions) => {
  if (!activeSession.value) return
  // 用户的提问
  const chatMessage = {
    id: '',
    aiSessionId: activeSession.value.id,
    content: message.content,
    type: 'USER',
    createdTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
  } satisfies AiMessage

  // 新建一个ChatGPT回复对象，不能重复使用同一个对象。
  responseMessage.value = {
    id: '',
    type: 'ASSISTANT',
    content: [{ urls: [] }],
    aiSessionId: activeSession.value.id,
    // 因为回复的消息没有id，所以统一将创建时间+index当作key
    createdTime: dayjs().format('YYYY-MM-DD HH:mm:ss')
  }
  const body: ChatMessageRequest = {
    message: chatMessage,
    chatParams: { tag: activeTag.value, ...message.options },
    chatOptions: {},
    imageOptions: activeSession.value.aiModel.options || {}
  }
  api.aiMessageForFrontController.generate({ body }).then(async (res) => {
    responseMessage.value.content[0].urls = res.results.map((item) => item.output.url)
    await api.aiMessageForFrontController.create({ body: chatMessage })
    await api.aiMessageForFrontController.create({ body: responseMessage.value })
    nextTick(() => {
      messageListRef.value?.scrollTo(0, messageListRef.value.scrollHeight)
    })
  })

  // 将两条消息显示在页面中
  activeSession.value.messages.push(...[chatMessage, responseMessage.value])
  nextTick(() => {
    messageListRef.value?.scrollTo(0, messageListRef.value.scrollHeight)
  })
}

const handleDelete = () => {
  sessionList.value
    .filter((session) => {
      return session.checked
    })
    .forEach((session) => {
      handleDeleteSession(session)
    })
}
</script>
<template>
  <!-- 最外层页面于窗口同宽，使聊天面板居中 -->
  <div class="home-view">
    <!-- 整个聊天面板 -->
    <div class="chat-panel" v-loading="loading">
      <!-- 左侧的会话列表 -->
      <div class="session-panel">
        <div class="title">AI助手</div>
        <!--        <div class="description">构建你的AI助手</div>-->
        <div class="top-button-wrapper">
          <el-popconfirm title="是否确认永久删除该聊天会话？" @confirm="handleDelete">
            <template #reference>
              <el-button type="danger" size="small" :icon="Delete">删除</el-button>
            </template>
          </el-popconfirm>
        </div>
        <div class="session-list" v-if="activeSession">
          <!-- for循环遍历会话列表用会话组件显示，并监听点击事件和删除事件。点击时切换到被点击的会话，删除时从会话列表中提出被删除的会话。 -->
          <!--  -->
          <session-item
            v-for="(session, index) in sessionList"
            :key="session.id"
            :active="session.id === activeSession.id"
            v-model:session="sessionList[index]"
            class="session"
            @click="activeSession = session"
            @delete="handleDeleteSession"
          ></session-item>
        </div>
        <div class="button-wrapper">
          <session-create-dialog class="new-session"></session-create-dialog>
        </div>
      </div>
      <!-- 右侧的消息记录 -->
      <div class="message-panel">
        <!-- 会话名称 -->
        <div class="header" v-if="activeSession">
          <div class="front">
            <!-- 如果处于编辑状态则显示输入框让用户去修改 -->
            <div v-if="isEdit" class="title">
              <!-- 按回车代表确认修改 -->
              <el-input
                v-model="activeSession.name"
                @keydown.enter="handleUpdateSession"
              ></el-input>
            </div>
            <!-- 否则正常显示标题 -->
            <div v-else class="title">{{ activeSession.name }}</div>
            <div class="description">{{ activeSession.messages.length }}条对话</div>
          </div>
          <!-- 尾部的编辑按钮 -->
          <div class="rear">
            <el-icon :size="20">
              <!-- 不处于编辑状态显示编辑按钮 -->
              <EditPen v-if="!isEdit" @click="isEdit = true" />
              <!-- 处于编辑状态显示取消编辑按钮 -->
              <Close v-else @click="isEdit = false"></Close>
            </el-icon>
          </div>
        </div>
        <el-divider :border-style="'solid'" />
        <div ref="messageListRef" class="message-list">
          <!-- 过渡效果 -->
          <transition-group name="list" v-if="activeSession">
            <message-row
              v-for="(message, index) in activeSession.messages"
              :key="message.createdTime + index"
              :message="message"
              :avatar="homeStore.userInfo?.avatar"
            ></message-row>
          </transition-group>
        </div>
        <!-- 监听发送事件 -->
        <message-input @send="handleSendMessage" v-if="activeSession"></message-input>
      </div>
      <div class="option-panel" v-if="activeSession && activeSession.aiModel">
        <el-form label-position="top">
          <chat-options
            v-if="activeTag == 'AIGC' || activeTag == 'VISION'"
            :factory="activeSession.aiModel.aiFactory.name"
            v-model="activeSession.aiModel.options"
          ></chat-options>
          <image-options
            v-if="activeTag == 'IMAGE'"
            :factory="activeSession.aiModel.aiFactory.name"
            v-model="activeSession.aiModel.options"
          ></image-options>
        </el-form>
      </div>
    </div>
  </div>
</template>
<style lang="scss" scoped>
@mixin chat-style($height, $width) {
  .session-list {
    height: calc($height + 200px);
  }
  //.message-panel {
  //  width: $width;
  //}
  .message-list {
    height: $height;
  }
}

.home-view {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  //align-items: center;
  @media screen and (max-width: 2560px) {
    @include chat-style(600px, 1200px);
  }
  @media screen and (max-width: 1920px) {
    @include chat-style(500px, 1000px);
  }
  @media screen and (max-width: 1440px) {
    @include chat-style(400px, 900px);
  }
  @media screen and (max-width: 1024px) {
    @include chat-style(300px, 800px);
  }

  .chat-panel {
    display: flex;
    background-color: white;
    width: 100%;
    .session-panel {
      width: 250px;
      padding: 20px;
      position: relative;
      border-right: 1px solid rgba(black, 0.07);
      background-color: rgb(231, 248, 255);
      /* 标题 */
      .title {
        margin-top: 20px;
        font-size: 20px;
      }

      /* 描述*/
      .description {
        color: rgba(black, 0.7);
        font-size: 14px;
        margin-top: 10px;
      }

      .top-button-wrapper {
        display: flex;
        /* 让内部的按钮显示在右侧 */
        justify-content: flex-end;
        /* 宽度和session-panel一样宽*/
        width: 100%;

        /* 按钮于右侧边界留一些距离 */
        .new-session {
          margin-right: 20px;
        }
      }

      .session-list {
        overflow-y: scroll;
        max-height: 70vh;
        margin-top: 20px;
        .session {
          /* 每个会话之间留一些间距 */
          margin-top: 20px;
        }
        .session:first-child {
          margin-top: 0;
        }
      }

      .button-wrapper {
        /* entity-panel是相对布局，这边的button-wrapper是相对它绝对布局 */
        position: absolute;
        bottom: 20px;
        left: 0;
        display: flex;
        /* 让内部的按钮显示在右侧 */
        justify-content: flex-end;
        /* 宽度和session-panel一样宽*/
        width: 100%;

        /* 按钮于右侧边界留一些距离 */
        .new-session {
          margin-right: 20px;
        }
      }
    }

    /* 右侧消息记录面板*/
    .message-panel {
      width: 100%;
      display: flex;
      flex-direction: column;
      .header {
        padding: 20px 20px 0 20px;
        display: flex;
        /* 会话名称和编辑按钮在水平方向上分布左右两边 */
        justify-content: space-between;

        /* 前部的标题和消息条数 */
        .front {
          .title {
            color: rgba(black, 0.7);
            font-size: 20px;
          }

          .description {
            margin-top: 10px;
            color: rgba(black, 0.5);
          }
        }

        /* 尾部的编辑和取消编辑按钮 */
        .rear {
          display: flex;
          align-items: center;
        }
      }

      .message-list {
        padding: 15px;
        width: 100%;
        flex: 1;
        box-sizing: border-box;
        // 消息条数太多时，溢出部分滚动
        overflow-y: scroll;
        // 当切换聊天会话时，消息记录也随之切换的过渡效果
        .list-enter-active,
        .list-leave-active {
          transition: all 0.5s ease;
        }

        .list-enter-from,
        .list-leave-to {
          opacity: 0;
          transform: translateX(30px);
        }
      }
    }
  }

  .option-panel {
    padding: 20px;
    border-left: 1px solid rgba(black, 0.07);
  }
}
</style>
