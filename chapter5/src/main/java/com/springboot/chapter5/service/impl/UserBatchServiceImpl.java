package com.springboot.chapter5.service.impl;

import com.springboot.chapter5.dao.UserDao;
import com.springboot.chapter5.pojo.User;
import com.springboot.chapter5.service.UserBatchService;
import com.springboot.chapter5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserBatchServiceImpl implements UserBatchService {
    @Autowired
    private UserService userService;

    /**
     * 父方法调子方法事务传播行为
     * @param userList
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int insertUsers(List<User> userList) {
        int count = 0;
        for (User user : userList) {
            // 调用子方法，将使用@Transactional定义的传播行为
            count += userService.insertUser(user);
        }
        return count;
    }
}
