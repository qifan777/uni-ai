package io.qifan.infrastructure.generator.processor.model.common;

import io.qifan.infrastructure.generator.processor.model.front.ItemField;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Field extends ModelElement {
    private Type type;
    private String fieldName;
    private String description;
    private ItemField itemField;

    @Override
    public Set<Type> getImportTypes() {
        return type.getImportTypes();
    }
}
