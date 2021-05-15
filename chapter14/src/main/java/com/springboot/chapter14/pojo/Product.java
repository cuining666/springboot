package com.springboot.chapter14.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

@Data
@Alias("product")
public class Product {
    private Long id;

    private String productName;

    private Integer stock;

    private BigDecimal price;

    private Integer version;

    private String note;

}