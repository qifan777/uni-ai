<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.common.Field" -->

<#if itemField.itemType.code==5>
private ${type.getTypeName()} ${getFieldName()+'Head'};
private ${type.getTypeName()} ${getFieldName()+'Tail'};
</#if>
private ${type.getTypeName()} ${getFieldName()};
