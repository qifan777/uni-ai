<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.controller.ControllerForFront" -->

package ${type.packagePath};
<#list importTypes as importType>
import ${importType.getTypePath()};
</#list>
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>

@RestController
@RequestMapping("front/${entityType.toFrontNameCase()}")
@AllArgsConstructor
@DefaultFetcherOwner(${entityType.typeName}Repository.class)
@Transactional
public class ${entityType.typeName}ForFrontController {
    private final ${entityType.typeName}Repository ${uncapitalizeTypeName}Repository;

    @GetMapping("{id}")
    public @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") ${entityType.typeName} findById(@PathVariable String id) {
        return ${uncapitalizeTypeName}Repository.findById(id, ${entityType.typeName}Repository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
    }

    @PostMapping("query")
    public Page< @FetchBy(value = "COMPLEX_FETCHER_FOR_FRONT") ${entityType.typeName}> query(@RequestBody QueryRequest<${entityType.typeName}Spec> queryRequest) {
        queryRequest.getQuery().setCreatorId(StpUtil.getLoginIdAsString());
        return ${uncapitalizeTypeName}Repository.findPage(queryRequest, ${entityType.typeName}Repository.COMPLEX_FETCHER_FOR_FRONT);
    }
    public String insert(${entityType.typeName}Input input) {
        return ${uncapitalizeTypeName}Repository.insert(input.toEntity()).id();
    }

    public String update(${entityType.typeName}Input input) {
        ${entityType.typeName} ${uncapitalizeTypeName} = ${uncapitalizeTypeName}Repository.findById(input.getId(), ${entityType.typeName}Repository.COMPLEX_FETCHER_FOR_FRONT).orElseThrow(() -> new BusinessException("数据不存在"));
        if (!${uncapitalizeTypeName}.creator().id().equals(StpUtil.getLoginIdAsString())) {
            throw new BusinessException("只能修改自己的数据");
        }
        return ${uncapitalizeTypeName}Repository.update(input.toEntity()).id();
    }

    @PostMapping("save")
    public String save(@RequestBody @Validated ${entityType.typeName}Input input) {
        return StringUtils.hasText(input.getId()) ? update(input) : insert(input);
    }

    @DeleteMapping
    public Boolean delete(@RequestBody List<String> ids) {
        ${uncapitalizeTypeName}Repository.findByIds(ids, ${entityType.typeName}Repository.COMPLEX_FETCHER_FOR_FRONT).forEach(${uncapitalizeTypeName} -> {
            if (!${uncapitalizeTypeName}.creator().id().equals(StpUtil.getLoginIdAsString())) {
                throw new BusinessException("只能删除自己的数据");
            }
        });
        ${uncapitalizeTypeName}Repository.deleteAllById(ids);
        return true;
    }
}
