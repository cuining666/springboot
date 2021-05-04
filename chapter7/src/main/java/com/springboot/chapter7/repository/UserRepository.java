package com.springboot.chapter7.repository;

import com.springboot.chapter7.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * MongoRepository所定义的方法
 * long count()  统计文档总数
 * void delete(Iterable<? extends T>)  删除多个文档
 * void delete(T)  删除指定的文档
 * void delete(id)  根据Id删除文档
 * boolean exists(Object)  判断是否存在对应的文档
 * boolean exists(id)  根据id判断是否存在对应的文档
 * List<T> findAll()  无条件查询所有文档
 * List<T> findAll(Iterable<? extends T>)  根据给出的文挡类型查询文档
 * List<T> findAll(Pageable)  根据分页条件查询文档
 * List<T> findAll(Sort)  查询所有文挡，并返回排序结果
 * T findOne(ID)  根据Id查询文档
 * S save(S extends T)  保存文档（如果已经存在文档，则更新文档，否则就新增文档）
 * List<S> save(Iterable<S extends T>)  保存文档列表（如果己经存在对应的文档，则更新文档，否则就新增文档）
 * S insert(S extends T)  新增文档
 * List<S> insert(Iterable<S extends T>)  保存文档列表
 */
// 标识为Dao层
@Repository
// 拓展MongoRepository接口
public interface UserRepository extends MongoRepository<User, Long> {

    /**
     * 符合JPA规范的命名方法，则不需要再实现该方法
     * 对满足条件的文档按照用户名进行模糊查询
     * MongoRepository接口，它指定了两个类型，一个是实体类型，这个实体类型要求标注＠Document,另一个是其主键的类型，这个类型要求标注＠Id
     * @param userName
     * @return
     */
    List<User> findByUserNameLike(String userName);

    /**
     * 使用id和用户名进行查询
     * 注解@Query阿拉伯数字指定参数的下标，以0开始
     * @param id
     * @param userName
     * @return
     */
    @Query("{'id':?0,'userName':?1}")
    User find(Long id, String userName);

    /**
     * 根据编号或者用户名查询用户
     * @param id
     * @param userName
     * @return
     */
    User findByIdOrUserName(Long id, String userName);
}
