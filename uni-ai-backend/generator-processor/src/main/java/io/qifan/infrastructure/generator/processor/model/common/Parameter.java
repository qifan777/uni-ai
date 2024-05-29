package io.qifan.infrastructure.generator.processor.model.common;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Parameter extends ModelElement {
    private Type type;
    private String variableName;

    @Override
    public Set<Type> getImportTypes() {
        return Set.of(type);
    }
}
