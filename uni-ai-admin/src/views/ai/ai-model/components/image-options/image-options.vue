<script setup lang="ts">
import type {AiFactoryType} from '@/apis/__generated/model/enums'
import {computed} from 'vue'
import DashScopeImageOptions from './dash-scope-image-options.vue'
import OpenAiImageOptions from './open-ai-image-options.vue'
import ZhiPuAiImageOptions from './zhi-pu-ai-image-options.vue'

const props = defineProps<{ factory: AiFactoryType }>()
const model = defineModel<any>({ default: {} })
const optionsComponent = computed(() => {
  switch (props.factory) {
    case 'DASH_SCOPE':
      return DashScopeImageOptions
    case 'OPEN_AI':
      return OpenAiImageOptions
    case 'ZHI_PU':
      return ZhiPuAiImageOptions
    default:
      return OpenAiImageOptions
  }
})
</script>

<template>
  <component :is="optionsComponent" v-model="model"></component>
</template>

<style scoped lang="scss"></style>
