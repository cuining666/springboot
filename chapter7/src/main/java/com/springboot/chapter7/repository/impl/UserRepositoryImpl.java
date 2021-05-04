package com.springboot.chapter7.repository.impl;

import com.springboot.chapter7.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * 这里并没有实现UserRepository接口，Spring JPA之所以能够找到这个类的findUserByIdOrUserName方法，
 * 是因为类的名称是“UserRepository”＋“Impl”，而方法名称也是相同的，只是Spring JPA给予我们默认的约定，
 * 只要按照这个约定它就能够找到对应的实现类和方法
 */
// 定义为数据访问层
@Repository
// 注意这里的类名，默认要是接口名称(UserRepository) + "impl"
// 这里Spring JPA会自动找到这个类作为接口方法的实现
public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    // 注意方法名称与接口定义的也需保持一致
    public User findByIdOrUserName(Long id, String userName) {
        // 根据ID查询准则
        Criteria criteriaId = Criteria.where("id").is(id);
        // 根据用户名查询准则
        Criteria criteriaUserName = Criteria.where("userName").is(userName);
        Criteria criteria = new Criteria();
        // 使用or操作符关联两个条件，形成或关系
        criteria.orOperator(criteriaId, criteriaUserName);
        Query query = Query.query(criteria);
        // 执行查询返回结果
        return mongoTemplate.findOne(query, User.class);
    }
}
