<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.View" -->
<script lang="ts" setup>
    import ${entityType.typeName}Table from './components/${entityType.toFrontNameCase()}-table.vue'
    import ${entityType.typeName}Query from './components/${entityType.toFrontNameCase()}-query.vue'
    import ${entityType.typeName}Dialog from './components/${entityType.toFrontNameCase()}-dialog.vue'
</script>
<template>
    <div class="${entityType.toFrontNameCase()}-view">
        <${entityType.toFrontNameCase()}-query></${entityType.toFrontNameCase()}-query>
        <${entityType.toFrontNameCase()}-dialog></${entityType.toFrontNameCase()}-dialog>
        <${entityType.toFrontNameCase()}-table></${entityType.toFrontNameCase()}-table>
    </div>
</template>

<style lang="scss" scoped>
.${entityType.toFrontNameCase()}-view {
    background: white;
    padding: 20px;
    border-radius: 5px;
}
</style>
