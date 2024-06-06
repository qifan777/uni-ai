<script setup lang="ts">
import ChatOptions from '@/views/ai/ai-model/components/chat-options/chat-options.vue'
import ImageOptions from '@/views/ai/ai-model/components/image-options/image-options.vue'
import { computed, reactive, ref } from 'vue'
import type { AiModelDto } from '@/apis/__generated/model/dto'
import RemoteSelect from '@/components/base/form/remote-select.vue'
import { Dictionaries } from '@/apis/__generated/model/enums/DictConstants'
import { aiModelQueryOptions } from '@/views/ai/ai-model/store/ai-model-store'
import { aiRoleQueryOptions } from '@/views/ai/ai-role/store/ai-role-store'
import type { ChatParams } from '@/apis/__generated/model/static'
import { aiCollectionQueryOptions } from '@/views/ai/ai-collection/store/ai-collection-store'
import type { FormRules } from 'element-plus'
import { api } from '@/utils/api-instance'

const rules = reactive<FormRules<ChatParams>>({
  tag: [{ required: true, message: '请选择类型', trigger: 'change' }],
  aiModelId: [{ required: true, message: '请选择模型', trigger: 'change' }]
})
const model = defineModel<ChatParams>({
  default: {
    aiModelId: '',
    tag: 'AIGC',
    aiRoleId: '',
    collectionId: '',
    options: {}
  }
})

const aiModel = ref<AiModelDto['AiModelRepository/COMPLEX_FETCHER_FOR_ADMIN']>()
const modelLabelProp = (row: AiModelDto['AiModelRepository/COMPLEX_FETCHER_FOR_ADMIN']) => {
  const tags = row.tagsView.map((tag) => Dictionaries.AiModelTag[tag.name].keyName).join('、')
  return `${row.name}(${Dictionaries.AiFactoryType[row.factory].keyName}-${tags})`
}
const handleAiModelChange = async (id: string) => {
  model.value.aiModelId = id
  aiModel.value = await api.aiModelForAdminController.findById({ id })
}
const handleAiModelQuery = async (query: string, id: string) => {
  const res = await aiModelQueryOptions(query, id, [model.value.tag])

  if (res.findIndex((item) => item.id === model.value.aiModelId) < 0 && res.length) {
    aiModel.value = res[0]
    model.value.aiModelId = res[0].id
  }
  return res
}
const tags = computed(() => {
  return [
    Dictionaries.AiModelTag.AIGC,
    Dictionaries.AiModelTag.IMAGE,
    Dictionaries.AiModelTag.VISION
  ]
})
</script>

<template>
  <el-form :rules="rules" :model="model" label-position="top">
    <el-form-item label="模型类别" prop="tag">
      <el-select v-model="model.tag">
        <el-option
          :key="tag.keyName"
          v-for="tag in tags"
          :label="tag.keyName"
          :value="tag.keyEnName"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="模型" prop="aiModelId">
      <remote-select
        :key="model.tag"
        :label-prop="modelLabelProp"
        :query-options="handleAiModelQuery"
        :model-value="model.aiModelId"
        @update:model-value="handleAiModelChange"
      ></remote-select>
    </el-form-item>
    <el-form-item label="知识库" v-if="model.tag == 'AIGC'" prop="collectionId">
      <remote-select
        label-prop="name"
        :query-options="aiCollectionQueryOptions"
        v-model="model.collectionId"
      ></remote-select>
    </el-form-item>
    <el-form-item prop="collectionId"> </el-form-item>
    <div v-if="aiModel">
      <chat-options
        v-if="model.tag == 'AIGC' || model.tag == 'VISION'"
        :factory="aiModel.factory"
        v-model="aiModel.options"
      ></chat-options>
      <image-options
        v-if="model.tag == 'IMAGE'"
        :factory="aiModel.factory"
        v-model="aiModel.options"
      ></image-options>
    </div>
    <el-form-item label="角色">
      <remote-select
        label-prop="name"
        :query-options="aiRoleQueryOptions"
        v-model="model.aiRoleId"
      ></remote-select>
    </el-form-item>
  </el-form>
</template>

<style scoped lang="scss"></style>
