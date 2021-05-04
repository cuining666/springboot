package com.springboot.chapter4.pojo;

import com.springboot.chapter4.converter.SexConverter;
import com.springboot.chapter4.enumeration.SexEnum;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;

@Data
// 标明是一个实体类
@Entity(name = "user")
// 定义映射的表
@Table(name = "t_user")
// Mybatis指定别名
@Alias(value = "user")
public class User {
    // 标明主键
    @Id
    // 主键策略-递增
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 定义属性和表的映射关系
    @Column(name = "user_name")
    private String userName;

    // 定义转换器
    @Convert(converter = SexConverter.class)
    // 性别枚举Mybatis需要使用typeHandler
    private SexEnum sex;

    private String note;

}