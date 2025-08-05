<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.controller.Controller" -->
package ${type.packagePath};
<#list importTypes as importType>
import ${importType.getTypePath()};
</#list>
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>

@RestController
@RequestMapping("/${entityType.toFrontNameCase()}")
@AllArgsConstructor
@DefaultFetcherOwner(${entityType.typeName}Repository.class)
@Transactional
public class  ${entityType.typeName}Controller {
    private final  ${entityType.typeName}Repository ${uncapitalizeTypeName}Repository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER")  ${entityType.typeName} findById(@PathVariable String id) {
        return ${uncapitalizeTypeName}Repository.findById(id,${entityType.typeName}Repository.COMPLEX_FETCHER).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page< @FetchBy(value = "COMPLEX_FETCHER")  ${entityType.typeName}> query(@RequestBody QueryRequest< ${entityType.typeName}Spec> queryRequest) {
        return ${uncapitalizeTypeName}Repository.findPage(queryRequest, ${entityType.typeName}Repository.COMPLEX_FETCHER);
    }

    public String insert(${entityType.typeName}Input input) {
        return ${uncapitalizeTypeName}Repository.insert(input.toEntity()).id();
    }

    public String update(${entityType.typeName}Input input) {
        return ${uncapitalizeTypeName}Repository.save(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated ${entityType.typeName}Input input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        ${uncapitalizeTypeName}Repository.deleteAllById(ids);
        return true;
    }
}