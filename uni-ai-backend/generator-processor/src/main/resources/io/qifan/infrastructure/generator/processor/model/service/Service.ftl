<#-- @ftlvariable name="" type="io.qifan.infrastructure.generator.processor.model.service.Service" -->
package ${type.packagePath};

<#list importTypes as importType>
    import ${importType.getTypePath()};
</#list>
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<#assign uncapitalizeTypeName = entityType.getUncapitalizeTypeName()>
@Transactional
@Service
open class NoticeService {
}