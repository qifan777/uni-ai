<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.Details" -->
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>
<script setup lang="ts">
    import { onActivated, reactive, ref } from 'vue'
    import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
    import type { ${entityType.typeName}Input } from '@/apis/__generated/model/static'
    import RemoteSelect from '@/components/base/form/remote-select.vue'
    import ImageUpload from '@/components/image/image-upload.vue'
    import KeyValueInput from '@/components/key-value/key-value-input.vue'
    import ValueInput from '@/components/key-value/value-input.vue'
    import { assertFormValidate } from '@/utils/common'
    import { api } from '@/utils/api-instance'
    import { useFormHelper } from '@/components/base/form/form-helper'
    const props = defineProps<{ id?: string }>()
    const formRef = ref<FormInstance>()
    const initForm: ${entityType.typeName}Input = {
    }
    const { formData: form, restForm } = useFormHelper<${entityType.typeName}Input>(initForm)
    const rules = reactive<FormRules<${entityType.typeName}Input>>({
        <#list getFormItems() as formItem>
        <#if !formItem.nullable>
        "${formItem.prop}": [{ required: true, message: '请输入${formItem.label}', trigger: "${(formItem.itemType.code==0)? string('change','blur')}"}],
        </#if>
        </#list>
    })
    const handleConfirm = () => {
        formRef.value?.validate(
            assertFormValidate(() =>
                api.${uncapitalizeTypeName}ForAdminController.save({ body: form.value }).then(async (res) => {
                    form.value.id = res
                    ElMessage.success('操作成功')
                })
            )
        )
    }
    onActivated(() => {
        if (props.id) {
            api.${uncapitalizeTypeName}ForAdminController.findById({ id: props.id }).then((res) => {
                form.value = res
            })
        } else {
            restForm()
        }
    })
</script>

<template>
    <div class="form">
        <el-form labelWidth="120" class="form" ref="formRef" :model="form" :rules="rules">
            <#list getFormItems() as itemField>
                <@includeModel object=itemField prefix="form"></@includeModel>
            </#list>
        </el-form>
        <el-row justify="center">
            <el-button type="primary" @click="handleConfirm">提交</el-button>
        </el-row>
    </div>
</template>

<style scoped lang="scss">
  .form {
    background: white;
    padding: 20px;
    border-radius: 5px;
  }
</style>
