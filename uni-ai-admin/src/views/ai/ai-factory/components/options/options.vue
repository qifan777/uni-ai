<script setup lang="ts">
import { computed } from 'vue'
import type { AiFactoryType } from '@/apis/__generated/model/enums'
import DashScopeOptions from './dash-scope-options.vue'
import OpenAiOptions from './open-ai-options.vue'
import QianFanOptions from './qian-fan-options.vue'
import KimiOptions from './kimi-options.vue'
import ZhiPuOptions from './zhi-pu-options.vue'

const props = defineProps<{ factory: AiFactoryType }>()
const model = defineModel<any>({ default: {} })
const optionsComponent = computed(() => {
  switch (props.factory) {
    case 'DASH_SCOPE':
      return DashScopeOptions
    case 'OPEN_AI':
      return OpenAiOptions
    case 'QIAN_FAN':
      return QianFanOptions
    case 'KIMI':
      return KimiOptions
    case 'ZHI_PU':
      return ZhiPuOptions
    default:
      return OpenAiOptions
  }
})
</script>

<template>
  <component :is="optionsComponent" v-model="model"></component>
</template>

<style scoped lang="scss"></style>
