<script setup lang="ts">
import type { AiFactoryType } from '@/apis/__generated/model/enums'
import { computed } from 'vue'
import SparkAiChatOptions from './spark-ai-chat-options.vue'
import DashScopeAiOptions from './dash-scope-ai-options.vue'
import OpenAiOptions from './open-ai-options.vue'
import QianfanAiOptions from './qianfan-ai-options.vue'
const props = defineProps<{ factory: AiFactoryType }>()
const model = defineModel<any>({ default: {} })
const optionsComponent = computed(() => {
  switch (props.factory) {
    case 'SPARK':
      return SparkAiChatOptions
    case 'DASH_SCOPE':
      return DashScopeAiOptions
    case 'OPEN_AI':
      return OpenAiOptions
    case 'QIAN_FAN':
      return QianfanAiOptions
    default:
      return OpenAiOptions
  }
})
</script>

<template>
  <component :is="optionsComponent" v-model="model"></component>
</template>

<style scoped lang="scss"></style>
