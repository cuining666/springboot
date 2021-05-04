package com.springboot.chapter6.service.impl;

import com.springboot.chapter6.dao.UserDao;
import com.springboot.chapter6.pojo.User;
import com.springboot.chapter6.service.UserService;
import com.sun.el.parser.AstFalse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 获取id，取参数id缓存用户
     * @Cacheable 表示先从缓存中通过定义的键查询，如果可以查询到数据，则返回，否则执行该方法，返回数据，并且将返回结果保存到缓存中
     * @param id
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Cacheable(value = "redisCache", key = "'redis_user_'+#id")
    public User getUser(Long id) {
        return userDao.selectByPrimaryKey(id);
    }

    /**
     * 插入用户，最后mybatis会回填id，取结果id缓存用户
     * @CachePut 表示将方法结果返回存放到缓存中
     * @param user
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @CachePut(value = "redisCache", key = "'redis_user_'+#result.id")
    public User insertUser(User user) {
        userDao.insert(user);
        return user;
    }

    /**
     * 更新数据后，更新缓存，如果condition配置项使结果返回为null，不缓存
     * @param id
     * @param userName
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @CachePut(value = "redisCache", condition = "#result != 'null'", key = "'redis_user_'+#id")
    public User updateByUserName(Long id, String userName) {
        // 此处调用getUser方法，该方法缓存注解失效
        // 所以这里还会执行sql，将查询到数据库最新数据
        // 因为Spring的缓存机制也是基于Spring AOP的原理，而在Spring中AOP是通过动态代理技术来实现的，这里的updateUserName方法调用getUser方法是类内部的自调用，
        // 并不存在代理对象的调用，这样便不会出现AOP，也就不会使用到标注在getUser上的缓存注解去获取缓存的值了，这是需要注意的地方
        // 解决方法：用一个Service去调用另一个Service，这样就是代理对象的调用，Spring才会将你的代码织入流程；也可以从Spring IoC 容器中获取代理对象去启用AOP。
        User user = getUser(id);
        if(user == null)
            return null;
        user.setUserName(userName);
        userDao.updateByPrimaryKey(user);
        return user;
    }

    /**
     * 命中率低，所以不采用缓存机制
     * @param userName
     * @param note
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<User> findUsers(String userName, String note) {
        return userDao.selectUserByUserNameAndNote(userName, note);
    }

    /**
     * 移除缓存
     * @CacheEvict 通过定义的键移除缓存，它有一个Boolean类型的配置项beforelnvocation，表示在方法之前或者之后移除缓存。因为其默认值为也lse ，所以默认为方法之后将缓存移除
     * @param id
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "redisCache",  key = "'redis_user_'+#id", beforeInvocation = false)
    public int deleteUser(Long id) {
        return userDao.deleteByPrimaryKey(id);
    }
}
