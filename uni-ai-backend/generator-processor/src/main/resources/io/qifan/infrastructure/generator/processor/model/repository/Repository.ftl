<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.repository.Repository" -->
package ${type.packagePath};

<#list importTypes as importType>
import ${importType.getTypePath()};
import ${importType.getPackagePath()+'.by'};
</#list>
import org.babyfish.jimmer.spring.repository.orderBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>

interface ${entityType.typeName}Repository : KRepository<${entityType.typeName}, String> {
    companion object {
        val COMPLEX_FETCHER_FOR_FRONT = newFetcher(${entityType.typeName}::class).by {
            allScalarFields()
            creator()
        }
        val COMPLEX_FETCHER_FOR_ADMIN = newFetcher(${entityType.typeName}::class).by {
            allScalarFields()
            creator {
                phone()
                nickname()
            }
            editor {
                phone()
                nickname()
            }
        }
    }

    fun findPage(page: Pageable, query: ${entityType.typeName}Spec, fetcher: Fetcher<${entityType.typeName}>): Page<${entityType.typeName}> {
        return sql.createQuery(${entityType.typeName}::class) {
            where(query)
            orderBy(page.sort)
            select(table.fetch(fetcher))
        }.fetchPage(
            page.pageNumber - 1,
            page.pageSize,
            pageFactory = SpringPageFactory.getInstance()
        )
    }
}