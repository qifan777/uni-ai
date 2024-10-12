<script setup lang="ts">
import type { AiFactoryType } from '@/apis/__generated/model/enums'
import { computed } from 'vue'
import DashScopeAiOptions from './dash-scope-ai-chat-options.vue'
import OpenAiOptions from './open-ai-chat-options.vue'
import QianFanAiOptions from './qian-fan-ai-chat-options.vue'
import KimiAiChatOptions from './kimi-ai-chat-options.vue'
import ZhiPuAiChatOptions from './zhi-pu-ai-chat-options.vue'

const props = defineProps<{ factory: AiFactoryType }>()
const model = defineModel<any>({ default: {} })
const optionsComponent = computed(() => {
  switch (props.factory) {
    case 'DASH_SCOPE':
      return DashScopeAiOptions
    case 'OPEN_AI':
      return OpenAiOptions
    case 'QIAN_FAN':
      return QianFanAiOptions
    case 'KIMI':
      return KimiAiChatOptions
    case 'ZHI_PU':
      return ZhiPuAiChatOptions
    default:
      return OpenAiOptions
  }
})
</script>

<template>
  <component :is="optionsComponent" v-model="model"></component>
</template>

<style scoped lang="scss"></style>
