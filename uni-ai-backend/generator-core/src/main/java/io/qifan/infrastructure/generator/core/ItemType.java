package io.qifan.infrastructure.generator.core;

import java.util.Arrays;

public enum ItemType {
    AUTO(-2, "自动判断"),
    SELECTABLE(0, "下拉框"),
    INPUT_TEXT(1, "文本输入框"),
    INPUT_TEXT_AREA(2, "文本域"),
    INPUT_NUMBER(3, "数字输入框"),
    PICTURE(4, "图片"),
    DATETIME(5, "日期"),
    ASSOCIATION_SELECT(6, "关系选择"),
    VALUE_INPUT(7, "值列表"),
    KEY_VALUE_INPUT(8, "键值对"),
    BOOLEAN(9, "布尔值"),
    NONE(-1, "不生成");

    private final Integer code;
    private final String name;

    ItemType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ItemType nameOf(String name) {
        return Arrays.stream(ItemType.values()).filter(type -> type.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("枚举不存在"));
    }

    public static ItemType codeOf(Integer code) {
        return Arrays.stream(ItemType.values()).filter(type -> type.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("枚举不存在"));
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}