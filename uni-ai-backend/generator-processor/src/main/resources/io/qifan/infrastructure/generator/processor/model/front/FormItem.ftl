<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.FormItem" -->
<#if getItemType().code!=-1>
    <el-form-item label="${getLabel()}" prop="${getProp()}">
        <#switch getItemType().code>
            <#case 0>
                <dict-select :dict-id="DictConstants.${getDictEnName()}" v-model="${ext.prefix}.${getProp()}"></dict-select>
                <#break >
            <#case 1>
                <el-input v-model.trim="${ext.prefix}.${getProp()}"></el-input>
                <#break>
            <#case 2>
                <el-input v-model="${ext.prefix}.${getProp()}" type="textarea"></el-input>
                <#break>
            <#case 3>
                <el-input-number v-model="${ext.prefix}.${getProp()}"></el-input-number>
                <#break>
            <#case 4>
                <image-upload v-model="${ext.prefix}.${getProp()}"></image-upload>
                <#break>
            <#case 5>
                <el-date-picker
                        v-model="${ext.prefix}.${getProp()}"
                        type="datetime"
                        placeholder="请选择日期"
                        value-format="YYYY-MM-DD HH:mm:ss"
                ></el-date-picker>
                <#break>
            <#case 6>
                <remote-select
                        v-model="${ext.prefix}.${getProp()}"
                        :label-prop="userLabelProp"
                        :query-options="userQueryOptions"
                ></remote-select>
                <#break>
            <#case 7>
                <remote-select label-prop="name" :query-options="${getAssociationType().getUncapitalizeTypeName()}QueryOptions" v-model="${ext.prefix}.${getProp()}"></remote-select>
                <#break>
            <#default >
                <el-input v-model="${ext.prefix}.${getProp()}"></el-input>
        </#switch>
    </el-form-item>
</#if>
