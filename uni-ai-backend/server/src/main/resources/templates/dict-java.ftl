<#-- @ftlvariable name="" type="io.qifan.server.dict.model.DictGenContext" -->
package io.qifan.server.dict.model;
import lombok.Getter;
import lombok.AllArgsConstructor;

public class DictConstants {
<#list getDictTypes() as type>
  public static final String ${getDictMap()[type][0].dictEnName()} = "${getDictMap()[type][0].dictEnName()}";
</#list>
<#list getDictTypes() as type>
  @Getter
  @AllArgsConstructor
  public enum ${type}{
    <#list getDictMap()[type] as dict>
        ${dict.keyEnName()}(${dict.keyId()?c}, "${dict.keyName()}", "${dict.keyEnName()}", ${dict.dictId()?c}, "${dict.dictName()}", "${dict.dictEnName()}", ${dict.orderNum()?c}),
    </#list>
  ;
  final int keyId;
  final String keyName;
  final String keyEnName;
  final int dictId;
  final String dictName;
  final String dictEnName;
  final int orderNum;
  }
</#list>
}
