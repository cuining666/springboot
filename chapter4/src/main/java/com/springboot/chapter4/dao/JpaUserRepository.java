package com.springboot.chapter4.dao;

import com.springboot.chapter4.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    /**
     * 按用户名称模糊查询
     * @param userName 用户名
     * @return
     */
    List<User> findByUserNameLike(String userName);

    /**
     * 按用户名称或备注进行模糊查询
     * @param userName 用户名
     * @param note 备注
     * @return
     */
    List<User> findByUserNameLikeOrNoteLike(String userName, String note);
}
