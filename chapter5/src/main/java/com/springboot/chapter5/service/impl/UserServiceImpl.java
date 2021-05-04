package com.springboot.chapter5.service.impl;

import com.springboot.chapter5.dao.UserDao;
import com.springboot.chapter5.pojo.User;
import com.springboot.chapter5.service.UserService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, ApplicationContextAware {
    @Autowired
    private UserDao userDao;
    private ApplicationContext applicationContext;

    @Override
    // 启用Spring数据库事务机制,采用了读写提交的隔离级别
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 1000)
    public User getUser(Long id) {
        User user = userDao.selectByPrimaryKey(id);
        return user;
    }

    @Override
    // Propagation.NESTED 的意思是如果外层没有事务，则内层会添加一个，而如果外层有事务，则内层会嵌套一层事务，即内层是有两层事务的
    // JPA 是不支持这种循环嵌套事务的，所以在运行的时候就会抛出异常
    // NESTED传播行为会沿用当前事务的隔离级别和锁等特性，而REQUIRES NEW则可以拥有自己独立的隔离级别和锁等特性
    //@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.NESTED, timeout = 3000)
    // 创建新事务，和父方法的事务互相独立
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW, timeout = 3000)
    // 沿用父方法的事务传播
    //@Transactional(isolation = Isolation.READ_COMMITTED, timeout = 3000)
    public int insertUser(User user) {
        return userDao.insert(user);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, timeout = 1000)
    public List<User> queryByUserName(String userName) {
        List<User> users = userDao.selectByUserName(userName);
        return users;
    }

    /**
     * 自调用事务传播行为失效
     * Spring数据库事务的约定，其实现原理是AOP，而AOP的原理是动态代理，在自调用的过程中，
     * 是类自身的调用，而不是代理对象去调用，那么就不会产生AOP，这样Spring
     * 就不能把你的代码织入到约定的流程中， 于是就产生了现在看到的失败场景。
     * 解决方法：用一个Service去调用另一个Service，如UserBatchService调用UserService，这样就是代理对象的调用，Spring才会将你的代码织入事务流程。
     * 也可以从Spring IoC 容器中获取代理对象去启用AOP。
     * @param userList
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int insertUsers(List<User> userList) {
        int count = 0;
        // 从ioc容器中取出代理对象
        UserService userService = applicationContext.getBean(UserService.class);
        for (User user : userList) {
            // 自调用
            // 使用代理对象调用方法插入用户，此时会织入spring数据库事务流程中
            count += userService.insertUser(user);
        }
        return count;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
