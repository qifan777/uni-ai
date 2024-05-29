package io.qifan.infrastructure.generator.processor.model.repository;

import io.qifan.infrastructure.generator.processor.model.common.ModelElement;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import io.qifan.infrastructure.generator.processor.model.front.QueryItem;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Repository extends ModelElement {
    private String sourcePath;
    private Type type;
    private Type entityType;
    private List<QueryItem> fields;
    @Override
    public Set<Type> getImportTypes() {

        return Set.of(entityType);
    }
}
