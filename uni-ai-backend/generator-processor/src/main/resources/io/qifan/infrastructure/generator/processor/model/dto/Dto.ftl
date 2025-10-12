<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.dto.Dto" -->
export ${entityType.typePath}

input ${type.typeName}Input {
    #allScalars(${type.typeName})
    id
<#list getFields() as field>
    <#switch field.itemType.code>
        <#case 6>
    id(${field.prop})
    </#switch>
</#list>
}

specification ${type.typeName}Spec {
    #allScalars
    <#list getFields() as field>
    <#switch field.itemType.code>
    <#case 1>
    like/i(${field.prop})
    <#break>
    <#case 2>
    like/i(${field.prop})
    <#break>
    <#case 5>
    ge(${field.prop})
    le(${field.prop})
        <#break>
    <#case 6>
    associatedIdEq(${field.prop})
        <#break>
    </#switch>
    </#list>
    associatedIdEq(creator)
}