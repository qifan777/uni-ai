<script setup lang="ts">
import type { MenuTreeDto } from '@/typings'

const defaultProps = {
  children: 'children',
  label: 'name'
}
defineProps<{ menuTree: MenuTreeDto[] }>()
defineSlots<{
  default(props: { node: { label: string }; data: MenuTreeDto }): void
}>()
</script>

<template>
  <el-tree :data="menuTree" :props="defaultProps" v-bind="$attrs">
    <template #default="{ node, data }: { node: { label: string }; data: MenuTreeDto }">
      <slot :node="node" :data="data"></slot>
    </template>
  </el-tree>
</template>

<style scoped lang="scss"></style>
