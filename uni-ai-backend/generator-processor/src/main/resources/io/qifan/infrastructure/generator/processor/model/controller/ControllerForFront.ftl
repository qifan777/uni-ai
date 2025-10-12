<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.controller.ControllerForFront" -->

package ${type.packagePath};
<#list importTypes as importType>
import ${importType.getTypePath()};
</#list>
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>

@RequestMapping("/front/${entityType.toFrontNameCase()}")
@RestController
@DefaultFetcherOwner(${entityType.typeName}Repository::class)
class ${entityType.typeName}ForFrontController(private val ${uncapitalizeTypeName}Repository: ${entityType.typeName}Repository) {
    @GetMapping
    fun findById(@RequestParam id: String): @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") ${entityType.typeName} =
        ${uncapitalizeTypeName}Repository.findById(id)
            .orElseThrow { BusinessException("信息不存在") }

    @PostMapping("query")
    fun query(@RequestBody queryRequest: QueryRequest<${entityType.typeName}Spec>): Page< @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") ${entityType.typeName}> {
    val copy = queryRequest.query.copy(creatorId = StpUtil.getLoginIdAsString())

    return ${uncapitalizeTypeName}Repository.findPage(queryRequest.toPageable(), copy, ${entityType.typeName}Repository.COMPLEX_FETCHER_FOR_FRONT)
}

    @PostMapping("save")
    fun save(@RequestBody input: ${entityType.typeName}Input): String {
        if (input.id != null) {
            val ${uncapitalizeTypeName} = ${uncapitalizeTypeName}Repository.findById(input.id, ${entityType.typeName}Repository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow()
            if (${uncapitalizeTypeName}.creator.id != StpUtil.getLoginIdAsString()) {
                throw BusinessException("您没有权限修改此信息")
            }
        }
        return ${uncapitalizeTypeName}Repository.save(input).id;
    }

    @PostMapping("delete")
    fun delete(@RequestBody ids: List<String>) {
        for (id in ids) {
            val ${uncapitalizeTypeName} = ${uncapitalizeTypeName}Repository.findById(id, ${entityType.typeName}Repository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow()
            if (${uncapitalizeTypeName}.creator.id != StpUtil.getLoginIdAsString()) {
                throw BusinessException("您没有权限删除此信息")
            }
        }
        ${uncapitalizeTypeName}Repository.deleteByIds(ids)
    }
}
