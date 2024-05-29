<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.front.FormItem" -->
<#if getItemType().code!=-1>
    <el-form-item label="${getLabel()}" prop="${getBind()}">
        <#switch getItemType().code>
            <#case 0>
                <dict-select :dict-id="DictConstants.${getDictEnName()}" v-model="${ext.prefix}.${getBind()}"></dict-select>
                <#break >
            <#case 1>
                <el-input v-model="${ext.prefix}.${getBind()}"></el-input>
                <#break>
            <#case 2>
                <el-input v-model="${ext.prefix}.${getBind()}" type="textarea"></el-input>
                <#break>
            <#case 3>
                <el-input-number v-model="${ext.prefix}.${getBind()}"></el-input-number>
                <#break>
            <#case 4>
                <image-upload v-model="${ext.prefix}.${getBind()}"></image-upload>
                <#break>
            <#case 5>
                <el-date-picker
                        v-model="${ext.prefix}.${getBind()}"
                        type="datetime"
                        placeholder="请选择日期"
                        value-format="YYYY-MM-DD HH:mm:ss"
                ></el-date-picker>
                <#break>
            <#case 6>
                <remote-select
                        v-model="${ext.prefix}.${getBind()}"
                        :label-prop="userLabelProp"
                        :query-options="userQueryOptions"
                ></remote-select>
                <#break>
            <#case 7>
                <remote-select label-prop="name" :query-options="${getBind().replaceAll("Id","")}QueryOptions" v-model="${ext.prefix}.${getBind()}"></remote-select>
                <#break>
            <#default >
                <el-input v-model="${ext.prefix}.${getBind()}"></el-input>
        </#switch>
    </el-form-item>
</#if>
