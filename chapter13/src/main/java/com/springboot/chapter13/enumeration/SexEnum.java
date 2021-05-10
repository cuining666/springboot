package com.springboot.chapter13.enumeration;

import lombok.Data;

/**
 * 用户性别枚举
 */
public enum SexEnum {
    MALE(1, "男"),
    FEMALE(0, "女");

    private int code;
    private String name;

    SexEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static SexEnum getSexEnum(int code) {
        SexEnum[] enums = SexEnum.values();
        for (SexEnum item: enums) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }
}
