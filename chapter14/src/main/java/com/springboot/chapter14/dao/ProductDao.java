package com.springboot.chapter14.dao;

import com.springboot.chapter14.pojo.Product;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao {
    int deleteByPrimaryKey(Long id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    /**
     * 减库存，而@Param标明Mybatis参数传递给后台
     * @param id
     * @param quantity
     * @return
     */
    int decreaseProduct(@Param("id") Long id,@Param("quantity") int quantity);
}