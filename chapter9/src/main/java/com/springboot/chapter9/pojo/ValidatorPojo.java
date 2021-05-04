package com.springboot.chapter9.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class ValidatorPojo {

    @NotNull(message = "id不能为空")
    private Long id;

    @Future(message = "只能是一个将来的日期")
//    @Past(message = "只能是一个过去的日期")
    // 日期格式转换
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date date;

    @NotNull
    // 最小值
    @DecimalMin(value = "0.1")
    // 最大值
    @DecimalMax(value = "100000.00")
    private Double doubleValue;

    @NotNull
    @Min(value = 1, message = "最小值为1")
    @Max(value = 88, message = "最大值为88")
    private Integer intValue;

    // 限定范围
    @Range(min = 1, max = 888, message = "范围为1至888")
    private Long range;

    @Email(message = "邮箱格式错误")
    private String email;

    @Size(min = 20, max = 30, message = "字符串长度要求20到30之间")
    private String size;
}
