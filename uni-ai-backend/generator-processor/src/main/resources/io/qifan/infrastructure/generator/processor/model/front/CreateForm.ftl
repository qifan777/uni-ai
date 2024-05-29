<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.CreateForm" -->
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>
<script lang="ts" setup>
    import {storeToRefs} from 'pinia'
    import { reactive, ref, watch } from 'vue'
    import {use${entityType.typeName}Store} from '../store/${entityType.toFrontNameCase()}-store'
    import { assertFormValidate, assertSuccess } from '@/utils/common'
    import {api} from '@/utils/api-instance'
    import ${'ImageUpload'} from '@/components/image/image-upload.vue'
    import ${'FooterButton'} from '@/components/base/dialog/footer-button.vue'
    import ${'DictSelect'} from '@/components/dict/dict-select.vue'
    import type { FormInstance, FormRules } from 'element-plus'
    import { ${'DictConstants'} } from '@/apis/__generated/model/enums/DictConstants'
    import { ${'userLabelProp'}, ${'userQueryOptions'} } from '@/views/user/store/user-store'
    import ${'RemoteSelect'} from '@/components/base/form/remote-select.vue'
    const ${uncapitalizeTypeName}Store = use${entityType.typeName}Store()
    const {closeDialog, reloadTableData} = ${uncapitalizeTypeName}Store
    const {createForm, dialogData} = storeToRefs(${uncapitalizeTypeName}Store)
    const createFormRef = ref<FormInstance>()
    const rules = reactive<FormRules<typeof createForm>>({
       <#list getFormItems() as formItem>
        <#if !formItem.nullable>
        "${formItem.bind}": [{ required: true, message: '请输入${formItem.label}', trigger: "${(formItem.itemType.code==0)? string('change','blur')}"}],
        </#if>
       </#list>
    })
    const init = async () => {
        dialogData.value.title = '创建'
    }
    watch(
        () => dialogData.value.visible,
        (value) => {
            if (value) {
                init()
            }
        },
        {immediate: true}
    )

    const handleConfirm = () => {
        createFormRef.value?.validate(
            assertFormValidate(() =>
                api.${uncapitalizeTypeName}ForAdminController.create({body: createForm.value}).then(async (res) => {
                    assertSuccess(res).then(() => {
                        closeDialog()
                        reloadTableData()
                    })
                })
            )
        )

    }
</script>
<template>
    <div class="create-form">
        <el-form labelWidth="120" class="form" ref="createFormRef" :model="createForm" :rules="rules">
            <#list getFormItems() as itemField>
                <@includeModel object=itemField prefix="createForm"></@includeModel>
            </#list>
        </el-form>
        <footer-button @close="closeDialog" @confirm="handleConfirm"></footer-button>
    </div>
</template>

<style lang="scss" scoped>
    .create-form{
      margin-right: 30px;
    }
</style>

