package com.springboot.chapter4.converter;

import com.springboot.chapter4.enumeration.SexEnum;

import javax.persistence.AttributeConverter;

public class SexConverter implements AttributeConverter<SexEnum, Integer> {
    /**
     * 将枚举转换为数据库列
     * @param sex
     * @return
     */
    @Override
    public Integer convertToDatabaseColumn(SexEnum sex) {
        return sex.getId();
    }

    /**
     * 将数据库列转换成枚举
     * @param id
     * @return
     */
    @Override
    public SexEnum convertToEntityAttribute(Integer id) {
        return SexEnum.getEnumById(id);
    }
}
