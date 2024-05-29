package io.qifan.infrastructure.generator.processor.utils;


import io.qifan.infrastructure.generator.processor.model.common.ParameterType;
import io.qifan.infrastructure.generator.processor.model.common.Type;

import javax.lang.model.element.TypeElement;

public class TypeUtils {


    public static ParameterType getParameterType(String typePath) {
        int start = typePath.length();
        int end = typePath.length();
        for (int i = 0; i < typePath.length(); i++) {
            if (typePath.charAt(i) == '<') {
                start = i;
                break;
            }
        }
        for (int i = typePath.length() - 1; i > 0; i--) {
            if (typePath.charAt(i) == '>') {
                end = i;
                break;
            }
        }
        ParameterType parameterType = null;
        if (start < end) {
            parameterType = getParameterType(typePath.substring(start + 1, end));
        }
        return new ParameterType(TypeUtils.getType(typePath.substring(0, start)), parameterType);
    }

    public static Type getType(Class<?> entityType, String packageName, String suffix) {
        String typeName = entityType.getSimpleName() + suffix;
        String packagePath = entityType.getTypeName()
                .replace(entityType.getSimpleName(), "") + packageName;
        return Type.builder()
                .typeName(typeName)
                .packagePath(packagePath)
                .build();
    }

    public static Type getType(TypeElement entityType) {
        return getType(entityType.getQualifiedName().toString());
    }


    public static Type getType(String typePath) {
        String[] splits = typePath.split("\\.");
        String typeName = splits[splits.length - 1];
        String packagePath = typePath.replace("." + splits[splits.length - 1], "");
        return getType(typeName, packagePath);
    }

    public static Type getType(String typeName, String packagePath) {
        return Type.builder()
                .typeName(typeName)
                .packagePath(packagePath)
                .build();
    }

    public static Type getType(Class<?> clazz) {
        return getType(clazz.getSimpleName(), clazz.getPackageName());
    }
}
