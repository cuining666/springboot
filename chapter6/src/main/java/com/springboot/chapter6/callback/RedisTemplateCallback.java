package com.springboot.chapter6.callback;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

/**
 * SessionCallback接口和RedisCallback接口，它们的作用是让RedisTemplate进行回调，通过它们可以在同一条连接下执行多个Redis命令
 */
public class RedisTemplateCallback {

    /**
     * 需要处理底层的转换规则，如果不考虑改写底层，尽量不使用它
     * @param redisTemplate
     */
//    public void useRedisCallback(RedisTemplate redisTemplate) {
//        redisTemplate.execute(new RedisCallback() {
//            @Override
//            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
//                redisConnection.set("key1".getBytes(), "value1".getBytes());
//                redisConnection.hSet("hash".getBytes(), "field".getBytes(), "hvalue".getBytes());
//                return null;
//            }
//        });
//    }

    /**
     * 高级接口，比较友好，一般情况下，优先使用它
     * @param redisTemplate
     */
    public void useSessionCallback(RedisTemplate redisTemplate) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.opsForValue().set("key1", "value1");
                redisOperations.opsForHash().put("hash","field","hvalue");
                return null;
            }
        });
    }

    // 使用Lambda表达式改写上述代码
    public void useRedisCallback(RedisTemplate redisTemplate) {
        redisTemplate.execute((RedisConnection redisConnection) -> {
            redisConnection.set("key1".getBytes(), "value1".getBytes());
            redisConnection.hSet("hash".getBytes(), "field".getBytes(), "hvalue".getBytes());
            return null;
        });
    }

//    public void useSessionCallback(RedisTemplate redisTemplate) {
//        redisTemplate.execute((RedisOperations redisOperations) -> {
//            redisOperations.opsForValue().set("key1", "value1");
//            redisOperations.opsForHash().put("hash","field","hvalue");
//            return null;
//        });
//    }
}
