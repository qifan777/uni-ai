<script lang="ts" setup>
import { inject, ref } from 'vue'
import { Position } from '@element-plus/icons-vue'
import ImageUpload from '@/components/image/image-upload.vue'
import { ElMessage } from 'element-plus'
import type { AiMessage } from '@/views/ai/ai-chat/store/ai-chat-store'
import type { ChatParams } from '@/apis/__generated/model/static'

const chatParams = inject<ChatParams>('chatParams')

// 发送消息消息事件
const emit = defineEmits<{
  send: [message: AiMessage['content']]
}>()
// 输入框内的消息
const message = ref<AiMessage['content']>([{ text: '' }, { image: '' }])
const sendMessage = () => {
  const msg = message.value.filter((item) => item.text || item.image)
  if (msg.length == 0) {
    ElMessage.warning('请输入消息')
    return
  }
  emit('send', msg)
  // 发送完清除
  message.value = [{ text: '' }, { image: '' }]
}
</script>

<template>
  <div class="message-input">
    <div class="input-wrapper">
      <!-- 按回车键发送，输入框高度三行 -->
      <el-input
        v-model="message[0].text"
        :autosize="false"
        :rows="3"
        class="input"
        resize="none"
        type="textarea"
        @keydown.enter.prevent="sendMessage"
      >
      </el-input>
      <div class="button-wrapper">
        <image-upload
          class="image"
          :size="40"
          v-model="message[1].image"
          v-if="chatParams && chatParams.tag == 'VISION'"
        ></image-upload>
        <el-button type="primary" @click="sendMessage">
          <el-icon class="el-icon--left">
            <Position />
          </el-icon>
          发送
        </el-button>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.message-input {
  padding: 20px 20px 0 20px;
  border-top: 1px solid rgba(black, 0.07);
  border-left: 1px solid rgba(black, 0.07);
  border-right: 1px solid rgba(black, 0.07);
  border-top-right-radius: 5px;
  border-top-left-radius: 5px;
  .el-form-item {
    align-items: center;
  }
}

.button-wrapper {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 20px;
  .image {
    margin-right: 20px;
  }
}
</style>
