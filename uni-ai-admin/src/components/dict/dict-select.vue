<script setup lang="ts">
import { onMounted, ref } from 'vue'
import type { DictDto } from '@/apis/__generated/model/dto'
import { queryDict } from '@/components/dict/dict'

const props = withDefaults(defineProps<{ dictId: number; modelValue?: string }>(), {
  modelValue: ''
})
const emit = defineEmits<{ 'update:modelValue': [value: string] }>()
const options = ref<DictDto['DictRepository/COMPLEX_FETCHER'][]>([])
onMounted(async () => {
  const res = queryDict({ dictId: props.dictId })
  if (res) {
    options.value = (await res).content
  }
})
</script>

<template>
  <el-select
    v-bind="$attrs"
    class="dict-select"
    :model-value="modelValue"
    clearable
    @change="
      (v) => {
        emit('update:modelValue', v)
      }
    "
  >
    <el-option
      v-for="option in options"
      :key="option.id"
      :value="option.keyEnName"
      :label="option.keyName"
    ></el-option>
  </el-select>
</template>

<style scoped lang="scss">
.dict-select {
  width: 160px;
}
</style>
