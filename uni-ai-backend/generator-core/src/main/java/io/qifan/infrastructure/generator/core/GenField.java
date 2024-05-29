package io.qifan.infrastructure.generator.core;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface GenField {

    // 字段注释
    String value() default "";

    // 生成前端表单时选用的组件根据此字段生成。比如：ElInput,ELInputNumber
    ItemType type() default ItemType.AUTO;

    // 字段的展示顺序
    int order() default 9999;

    // 当类型为下拉框时，填写字典编号
    String dictEnName() default "";
}
