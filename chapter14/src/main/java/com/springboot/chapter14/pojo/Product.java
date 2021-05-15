package com.springboot.chapter14.pojo;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Alias("product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1735931943761678703L;

    private Long id;

    private String productName;

    private Integer stock;

    private BigDecimal price;

    private Integer version;

    private String note;

}