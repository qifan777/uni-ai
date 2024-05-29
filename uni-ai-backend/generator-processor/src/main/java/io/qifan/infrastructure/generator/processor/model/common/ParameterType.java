package io.qifan.infrastructure.generator.processor.model.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ParameterType extends Type {
    private ParameterType parameterType;

    public ParameterType(Type type, ParameterType parameterType) {
        super(type.getTypeName(), type.getPackagePath());
        this.parameterType = parameterType;
    }

    public String getCurrentTypeName() {
        return typeName;
    }

    @Override
    public String getTypeName() {
        String typeName = super.typeName;
        if (parameterType != null) {
            typeName = typeName + "<" + parameterType.getTypeName() + ">";
        }
        return typeName;
    }

    @Override
    public Set<Type> getImportTypes() {
        HashSet<Type> types = new HashSet<>(super.getImportTypes());
        if (parameterType != null) {
            types.addAll(parameterType.getImportTypes());
        }
        return types;
    }
}
