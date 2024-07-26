<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.TableItem" -->
<#if getItemType().code!=-1>
    <el-table-column label="${getLabel()}" prop="${getProp()}" sortable="custom" show-overflow-tooltip width="120">
        <template v-slot:default="{row}:${entityType.typeName}Scope">
            <#switch getItemType().code>
                <#case 0>
                    <dict-column :dict-id="DictConstants.${getDictEnName()}" :value="row.${getProp()}"></dict-column>
                    <#break>
                <#case 4>
                    <el-avatar :src="row.${getProp()}" alt=""></el-avatar>
                    <#break>
                <#case 6>
                    {{row.${getProp().replaceAll("Id","")}.name}}
                    <#break>
                <#case 9>
                    <el-switch v-model="row.${getProp()}" disabled></el-switch>
                    <#break>
                <#default>
                    {{row.${getProp()}}}
            </#switch>
        </template>
    </el-table-column>
</#if>
