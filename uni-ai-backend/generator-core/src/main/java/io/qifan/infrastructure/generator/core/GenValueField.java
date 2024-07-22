package io.qifan.infrastructure.generator.core;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface GenValueField {
    // 字段注释
    String label() default "";

    String prop() default "";

    int order() default 9999;
}
