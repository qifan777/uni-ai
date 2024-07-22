<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.dto.Dto" -->
export ${entityType.typePath}

input ${type.typeName}Input {
    #allScalars(${type.typeName})
    id
<#list getFields() as field>
    <#switch field.itemType.code>
        <#case 7>
    id(${field.fieldName})
    </#switch>
</#list>
}

specification ${type.typeName}Spec {
    #allScalars
    <#list getFields() as field>
    <#switch field.itemType.code>
    <#case 1>
    like/i(${field.fieldName})
    <#break>
    <#case 2>
    like/i(${field.fieldName})
    <#break>
    <#case 5>
    ge(${field.fieldName})
    le(${field.fieldName})
        <#break>
    <#case 7>
    id(${field.fieldName})
        <#break>
    </#switch>
    </#list>
    id(creator)
}