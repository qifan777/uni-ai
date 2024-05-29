package io.qifan.infrastructure.generator.processor.utils;


import io.qifan.infrastructure.generator.core.GenField;
import io.qifan.infrastructure.generator.core.ItemType;
import io.qifan.infrastructure.generator.processor.model.common.Field;
import io.qifan.infrastructure.generator.processor.model.common.Type;
import io.qifan.infrastructure.generator.processor.model.front.ItemField;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;
import org.reflections.util.ReflectionUtilsPredicates;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Slf4j
public class FieldUtils {

    public static List<ItemField> getItemFields(Class<?> typeElement) {
        List<Method> methods = ReflectionUtils.getAllMethods(typeElement,
                        ReflectionUtilsPredicates.withAnnotation(GenField.class))
                .stream().sorted(Comparator.comparingInt(o -> o.getAnnotation(GenField.class).order()))
                .toList();
        List<ItemField> itemFields = new ArrayList<>();
        methods.forEach(method -> {
            GenField annotation = method.getAnnotation(GenField.class);
            itemFields.add(getItemField(annotation, method, typeElement));
        });
        return itemFields;
    }

    public static ItemType itemType(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType.equals(String.class)) {
            return ItemType.INPUT_TEXT;
        } else if (returnType.equals(Integer.class) ||
                returnType.equals(Long.class) || returnType.getTypeName().equals("int")) {
            return ItemType.INPUT_NUMBER;
        } else if (returnType.equals(Date.class) || returnType.equals(LocalDateTime.class)
                || returnType.equals(LocalDate.class) || returnType.equals(LocalTime.class)) {
            return ItemType.DATETIME;
        }
        return ItemType.INPUT_NUMBER;
    }

    public static Boolean isNullable(Method method) {
        Class<?> returnType = method.getReturnType();
        // 原生类型不能为null
        if (returnType.isPrimitive()) {
            return false;
        }
        if (method.isAnnotationPresent(
                NotNull.class)) {
            return false;
        }
        if (method.isAnnotationPresent(Null.class)) {
            return true;
        }
        return returnType.equals(Integer.class) || returnType.equals(Long.class) ||
                returnType.equals(Boolean.class) || returnType.equals(Double.class)
                || returnType.equals(Float.class) || returnType.equals(Short.class) || returnType.equals(Byte.class) || returnType.equals(Character.class);
    }

    public static ItemField getItemField(GenField genItem, Method field,
                                         Class<?> typeElement) {
        return new ItemField()
                .setEntityType(TypeUtils.getType(typeElement))
                .setLabel(genItem.value())
                .setBind(field.getName())
                .setFieldName(field.getName())
                .setDictEnName(String.valueOf(genItem.dictEnName()))
                .setItemType(genItem.type().equals(ItemType.AUTO) ? itemType(field) : genItem.type())
                .setNullable(isNullable(field));
    }

    public static List<Field> getFields(Class<?> typeElement) {
        Set<Method> fields = ReflectionUtils.getAllMethods(typeElement);
        return fields.stream()
                .map(variableElement -> {
                    String typePath = variableElement.getGenericReturnType().getTypeName();
                    Type type = TypeUtils.getType(typePath);
                    Optional<GenField> genFieldOptional =
                            Optional.ofNullable(variableElement.getAnnotation(GenField.class));

                    if (isParameterType(typePath)) {
                        type = TypeUtils.getParameterType(typePath);
                    }
                    return Field.builder()
                            .type(type)
                            .description(genFieldOptional.map(GenField::value).orElse(""))
                            .fieldName(variableElement.getName())
                            .itemField(genFieldOptional.map(
                                            genField -> getItemField(genField, variableElement, typeElement))
                                    .orElse(new ItemField()
                                            .setNullable(isNullable(variableElement))
                                            .setItemType(itemType(variableElement))
                                            .setFieldName(variableElement.getName())
                                            .setBind(variableElement.getName())
                                            .setEntityType(TypeUtils.getType(typeElement))))
                            .build();
                }).toList();
    }


    public static boolean isParameterType(String typePath) {
        return typePath.contains("<");
    }
}
