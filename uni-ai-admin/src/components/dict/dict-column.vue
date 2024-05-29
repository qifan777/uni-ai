<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import type { DictDto } from '@/apis/__generated/model/dto'
import { queryDict } from '@/components/dict/dict'

const props = withDefaults(defineProps<{ dictId: number; value?: string }>(), { value: '' })
const options = ref<DictDto['DictRepository/COMPLEX_FETCHER'][]>([])
onMounted(async () => {
  const res = queryDict({ dictId: props.dictId })
  if (res) {
    options.value = (await res).content
  }
})
const keyName = computed(() => {
  const option = options.value.find((option) => {
    return option.keyEnName === props.value
  })
  return option ? option.keyName : ''
})
</script>

<template>
  <div>{{ keyName }}</div>
</template>

<style scoped lang="scss"></style>
