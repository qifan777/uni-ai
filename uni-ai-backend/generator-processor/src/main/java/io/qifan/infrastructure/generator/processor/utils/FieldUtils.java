package io.qifan.infrastructure.generator.processor.utils;


import io.qifan.infrastructure.generator.core.*;
import io.qifan.infrastructure.generator.processor.model.front.ItemField;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.extern.slf4j.Slf4j;
import org.reflections.ReflectionUtils;
import org.reflections.util.ReflectionUtilsPredicates;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class FieldUtils {

    public static List<ItemField> getItemFields(Class<?> typeElement) {
        List<Class<? extends Annotation>> classList = List.of(
                GenTextField.class,
                GenTextAreaField.class,
                GenDateTimeField.class,
                GenDictField.class,
                GenImageField.class,
                GenNumberField.class,
                GenAssociationField.class,
                GenValueField.class,
                GenKeyValueField.class);
        List<Method> methods = classList.stream().flatMap(annotationClass -> {
                    return ReflectionUtils.getAllMethods(typeElement,
                                    ReflectionUtilsPredicates.withAnnotation(annotationClass))
                            .stream();
                })
                .toList();
        List<ItemField> itemFields = new ArrayList<>();
        methods.forEach(method -> {
            ItemField itemField = new ItemField()
                    .setEntityType(TypeUtils.getType(typeElement))
                    .setFieldName(method.getName())
                    .setNullable(isNullable(method));
            if (method.isAnnotationPresent(GenTextField.class)) {
                GenTextField annotation = method.getAnnotation(GenTextField.class);
                itemField.setLabel(annotation.label())
                        .setProp(annotation.prop())
                        .setOrder(annotation.order())
                        .setItemType(ItemType.INPUT_TEXT);
            }
            if (method.isAnnotationPresent(GenTextAreaField.class)) {
                GenTextAreaField annotation = method.getAnnotation(GenTextAreaField.class);
                itemField.setLabel(annotation.label())
                        .setProp(annotation.prop())
                        .setOrder(annotation.order())
                        .setItemType(ItemType.INPUT_TEXT_AREA);
            }
            if (method.isAnnotationPresent(GenDateTimeField.class)) {
                GenDateTimeField annotation = method.getAnnotation(GenDateTimeField.class);
                itemField.setLabel(annotation.label())
                        .setProp(annotation.prop())
                        .setOrder(annotation.order())
                        .setItemType(ItemType.DATETIME);
            }
            if (method.isAnnotationPresent(GenDictField.class)) {
                GenDictField annotation = method.getAnnotation(GenDictField.class);
                itemField.setLabel(annotation.label())
                        .setProp(annotation.prop())
                        .setOrder(annotation.order())
                        .setDictEnName(annotation.dictEnName())
                        .setItemType(ItemType.SELECTABLE);
            }
            if (method.isAnnotationPresent(GenImageField.class)) {
                GenImageField annotation = method.getAnnotation(GenImageField.class);
                itemField.setLabel(annotation.label())
                        .setProp(annotation.prop())
                        .setOrder(annotation.order())
                        .setItemType(ItemType.PICTURE);
            }
            if (method.isAnnotationPresent(GenNumberField.class)) {
                GenNumberField annotation = method.getAnnotation(GenNumberField.class);
                itemField.setLabel(annotation.label())
                        .setProp(annotation.prop())
                        .setOrder(annotation.order())
                        .setItemType(ItemType.INPUT_NUMBER);
            }
            if (method.isAnnotationPresent(GenAssociationField.class)) {
                GenAssociationField annotation = method.getAnnotation(GenAssociationField.class);
                itemField.setLabel(annotation.label())
                        .setProp(annotation.prop())
                        .setOrder(annotation.order())
                        .setAssociationType(TypeUtils.getType(method.getReturnType()))
                        .setItemType(ItemType.ASSOCIATION_SELECT);
            }
            if (method.isAnnotationPresent(GenValueField.class)) {
                GenValueField annotation = method.getAnnotation(GenValueField.class);
                itemField.setLabel(annotation.label())
                        .setProp(annotation.prop())
                        .setOrder(annotation.order())
                        .setItemType(ItemType.VALUE_INPUT);
            }
            if (method.isAnnotationPresent(GenKeyValueField.class)) {
                GenKeyValueField annotation = method.getAnnotation(GenKeyValueField.class);
                itemField.setLabel(annotation.label())
                        .setProp(annotation.prop())
                        .setOrder(annotation.order())
                        .setItemType(ItemType.KEY_VALUE_INPUT);
            }
            if (!StringUtils.hasText(itemField.getProp())) {
                itemField.setProp(method.getName());
            }
            itemFields.add(itemField);
        });
        itemFields.sort(Comparator.comparingInt(ItemField::getOrder));
        return itemFields;
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
}
