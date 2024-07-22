package io.qifan.infrastructure.generator.core;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface GenDictField {
    String label() default "";

    String prop() default "";

    int order() default 9999;

    // 当类型为下拉框时，填写字典编号
    String dictEnName() default "";
}
