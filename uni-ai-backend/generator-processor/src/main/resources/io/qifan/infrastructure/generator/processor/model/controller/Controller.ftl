<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.controller.Controller" -->
package ${type.packagePath};
<#list importTypes as importType>
import ${importType.getTypePath()};
</#list>
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>

@RestController
@RequestMapping("${entityType.toFrontNameCase()}")
@AllArgsConstructor
@DefaultFetcherOwner(${entityType.typeName}Repository.class)
public class ${getType().getTypeName()} {
    private final ${entityType.typeName}Service ${uncapitalizeTypeName}Service;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER") ${entityType.typeName} findById(@PathVariable String id) {
        return ${uncapitalizeTypeName}Service.findById(id);
    }

    @PostMapping("query")
    public Page< @FetchBy(value = "COMPLEX_FETCHER") ${entityType.typeName}> query(@RequestBody QueryRequest<${entityType.typeName}Spec> queryRequest) {
        return ${uncapitalizeTypeName}Service.query(queryRequest);
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated ${entityType.typeName}Input ${uncapitalizeTypeName}) {
        return ${uncapitalizeTypeName}Service.save(${uncapitalizeTypeName});
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        return ${uncapitalizeTypeName}Service.delete(ids);
    }
}