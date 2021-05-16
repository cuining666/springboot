package com.springboot.chapter15.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1735931943761678703L;

    private Long id;

    private String productName;

    private Integer stock;

    private BigDecimal price;

    private Integer version;

    private String note;

}