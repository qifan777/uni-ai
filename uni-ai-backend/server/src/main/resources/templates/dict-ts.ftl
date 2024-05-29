<#-- @ftlvariable name="" type="io.qifan.mall.server.dict.model.DictGenContext" -->

export const DictConstants = {
<#list getDictTypes() as type>
    ${getDictMap()[type][0].dictEnName()}: ${getDictMap()[type][0].dictId()?c},
</#list>
}
export const Dictionaries = {
    <#list getDictTypes() as type>
    ${type}: {
      <#list getDictMap()[type] as dict>
        ${dict.keyEnName()}: {
          keyId: ${dict.keyId()?c},
          keyName: '${dict.keyName()}',
          keyEnName: '${dict.keyEnName()}',
          dictId: ${dict.dictId()?c},
          dictName: '${dict.dictName()}',
          dictEnName: '${dict.dictEnName()}',
          orderNum: ${dict.orderNum()?c}
        },
      </#list>
      },
    </#list>
}