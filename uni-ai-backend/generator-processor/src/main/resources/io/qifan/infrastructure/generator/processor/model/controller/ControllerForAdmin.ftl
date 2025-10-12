<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.controller.ControllerForAdmin" -->
package ${type.packagePath};
<#list importTypes as importType>
import ${importType.getTypePath()};
</#list>
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>

@RequestMapping("admin/${entityType.toFrontNameCase()}")
@RestController
@DefaultFetcherOwner(${entityType.typeName}Repository::class)
@SaCheckPermission("/${entityType.toFrontNameCase()}")
class ${entityType.typeName}ForAdminController(private val ${uncapitalizeTypeName}Repository: ${entityType.typeName}Repository) {
    @GetMapping
    fun findById(@RequestParam id: String): @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") ${entityType.typeName} =
        ${uncapitalizeTypeName}Repository.findById(id)
            .orElseThrow { BusinessException("信息不存在") }

    @PostMapping("query")
    fun query(@RequestBody queryRequest: QueryRequest<${entityType.typeName}Spec>): Page< @FetchBy(value = "COMPLEX_FETCHER_FOR_ADMIN") ${entityType.typeName}> {
        return ${uncapitalizeTypeName}Repository.findPage(
            queryRequest.toPageable(),
            queryRequest.query,
            ${entityType.typeName}Repository.COMPLEX_FETCHER_FOR_ADMIN
        )
    }

    @PostMapping("save")
    fun save(@RequestBody input: ${entityType.typeName}Input): String {
        return ${uncapitalizeTypeName}Repository.save(input).id;
    }

    @PostMapping("delete")
    fun delete(@RequestBody ids: List<String>) {
        ${uncapitalizeTypeName}Repository.deleteByIds(ids)
    }
}