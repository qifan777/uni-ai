<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.FormItem" -->
<#if getItemType().code!=-1 &&getItemType().code!=4>
    <el-form-item label="${getLabel()}">
        <#switch getItemType().code>
            <#case 0>
                <dict-select :dict-id="DictConstants.${getDictEnName()}" v-model="query.${getProp()}"></dict-select>
                <#break>
            <#case 3>
                <el-input-number v-model="query.${getProp()}" controls-position="right"></el-input-number>
                <#break>
            <#case 5>
                <datetime-picker
                    v-model:min-date-time="query.min${getProp()?cap_first}"
                    v-model:max-date-time="query.max${getProp()?cap_first}"
                >
                </datetime-picker>
                <#break>
            <#case 6>
                 <remote-select label-prop="name" :query-options="${getAssociationType().getUncapitalizeTypeName()}QueryOptions" v-model="query.${getProp()}"></remote-select>
                <#break>
            <#case 7>
                <#break >
            <#case 8>
                <#break >
            <#case 9>
                <el-switch v-model="query.${getProp()}"></el-switch>
                <#break>
            <#case 10>
                <#break >
            <#default>
                <el-input v-model.trim="query.${getProp()}"></el-input>
        </#switch>
    </el-form-item>
</#if>