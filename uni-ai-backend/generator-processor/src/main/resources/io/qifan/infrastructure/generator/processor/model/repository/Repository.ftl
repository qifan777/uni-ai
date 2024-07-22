<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.repository.Repository" -->
package ${type.packagePath};

<#list importTypes as importType>
import ${importType.getTypePath()};
</#list>
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>

public interface ${type.typeName} extends JRepository<${entityType.typeName}, String> {
    ${entityType.typeName}Table t = ${entityType.typeName}Table.$;
    ${entityType.typeName}Fetcher COMPLEX_FETCHER_FOR_ADMIN = ${entityType.typeName}Fetcher.$.allScalarFields()
        .creator(UserFetcher.$.phone().nickname())
        .editor(UserFetcher.$.phone().nickname());
    ${entityType.typeName}Fetcher COMPLEX_FETCHER_FOR_FRONT = ${entityType.typeName}Fetcher.$.allScalarFields()
            .creator(true);
  default Page<${entityType.typeName}> findPage(QueryRequest<${entityType.typeName}Spec> queryRequest,
      Fetcher<${entityType.typeName}> fetcher) {
    ${entityType.typeName}Spec query = queryRequest.getQuery();
    Pageable pageable = queryRequest.toPageable();
    return sql().createQuery(t)
        .where(query)
        .orderBy(SpringOrders.toOrders(t, pageable.getSort()))
        .select(t.fetch(fetcher))
        .fetchPage(queryRequest.getPageNum() - 1, queryRequest.getPageSize(),
            SpringPageFactory.getInstance());
  }
}