<script lang="ts" setup>
import { inject, reactive, ref } from 'vue'
import { Position } from '@element-plus/icons-vue'
import type { MessageWithOptions } from '@/views/ai/ai-chat/store/ai-chat-store'
import ImageUpload from '@/components/image/image-upload.vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import type { AiModelTag } from '@/apis/__generated/model/enums'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { aiCollectionQueryOptions } from '@/views/ai/ai-collection/store/ai-collection-store'
import { assertFormValidate } from '@/utils/common'

const rules = reactive<FormRules<MessageWithOptions['options']>>({
  collectionId: [{ required: true, message: '请选择知识库', trigger: 'change' }]
})

const formRef = ref<FormInstance>()
// 发送消息消息事件
const emit = defineEmits<{
  send: [message: MessageWithOptions]
}>()
const activeTag = inject<AiModelTag>('activeTag', 'AIGC')
// 输入框内的消息
const message = ref<MessageWithOptions>({
  content: [{ text: '' }, { image: '' }],
  options: { knowledge: false, collectionId: '' }
})
const sendMessage = () => {
  formRef.value?.validate(
    assertFormValidate(() => {
      const msg = message.value.content.filter((item) => item.text || item.image)
      if (msg.length == 0) {
        ElMessage.warning('请输入消息')
        return
      }
      emit('send', { content: msg, options: message.value.options })
      // 发送完清除
      message.value.content = [{ text: '' }, { image: '' }]
    })
  )
}
</script>

<template>
  <div class="message-input">
    <el-form
      :rules="rules"
      :model="message.options"
      ref="formRef"
      class="tools"
      inline
      size="small"
      label-position="left"
    >
      <el-form-item label="知识库">
        <el-switch v-model="message.options.knowledge"></el-switch>
      </el-form-item>
      <el-form-item v-if="message.options.knowledge" prop="collectionId">
        <remote-select
          label-prop="name"
          :query-options="aiCollectionQueryOptions"
          v-model="message.options.collectionId"
        ></remote-select>
      </el-form-item>
      <el-form-item label="图片" v-if="activeTag == 'VISION'">
        <image-upload :size="40" v-model="message.content[1].image"></image-upload>
      </el-form-item>
    </el-form>
    <div class="input-wrapper">
      <!-- 按回车键发送，输入框高度三行 -->
      <el-input
        v-model="message.content[0].text"
        :autosize="false"
        :rows="3"
        class="input"
        resize="none"
        type="textarea"
        @keydown.enter="sendMessage"
      >
      </el-input>
      <div class="button-wrapper">
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
}
</style>
