<script setup lang="tsx">
import type {AiFactoryType} from '@/apis/__generated/model/enums'
import {computed} from 'vue'
import ZhiPuEmbeddingOptions from './zhi-pu-embedding-options.vue'
import DashScopeEmbeddingOptions from './dash-scope-embedding-options.vue'

const props = defineProps<{ factory: AiFactoryType }>()
const model = defineModel<any>({default: {}})
const optionsComponent = computed(() => {
  switch (props.factory) {
    case 'ZHI_PU':
      return ZhiPuEmbeddingOptions
    case 'DASH_SCOPE':
      return DashScopeEmbeddingOptions
    default:
      return <div></div>
  }
})
</script>

<template>
  <component :is="optionsComponent" v-model="model"></component>
</template>

<style scoped lang="scss"></style>
