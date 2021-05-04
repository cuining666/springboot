package com.springboot.chapter6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    /**
     * 连接池配置信息
     * @return
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大空闲数
        jedisPoolConfig.setMaxIdle(30);
        // 最大连接数
        jedisPoolConfig.setMaxTotal(50);
        // 最大等待毫秒数
        jedisPoolConfig.setMaxWaitMillis(2000);
        return jedisPoolConfig;
    }

    /**
     * jedis连接工厂
     * @param jedisPoolConfig
     * @return
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        // 设置redis服务器的host或者ip地址
        redisStandaloneConfiguration.setHostName("192.168.56.102");
        // 设置redis服务器的端口号
        redisStandaloneConfiguration.setPort(6379);
        // 设置redis服务器的密码
        redisStandaloneConfiguration.setPassword("root");
        // 获取默认的连接池构造
        // 这里需要注意的是JedisConnectionFactory对于Standalone模式的没有（RedisStandaloneConfiguration，JedisPoolConfig）的构造函数，对此们用JedisClientConfiguration接口的builder方法实例化一个构造器，还得类型转换
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcf = (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        // 修改连接池配置
        jpcf.poolConfig(jedisPoolConfig);
        // 通过构造器来构造jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = jpcf.build();
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    /**
     * RedisTemplate中的序列化器属性
     * defaultSerializer 默认序列化器 如果没有设置，则使用JdkSerializationRedisSerializer
     * keySerializer Redis键序列化器 如果没有设置，则使用默认序列化器
     * valueSerializer Redis值序列化器 如果没有设置，则使用默认序列化器
     * hashKeySerializer Redis散列结构field序列化器 如果没有设置，则使用默认序列化器
     * hashValueSerializer Redis散列结构value序列化器 如果没有设置，则使用默认序列化器
     * stringSerializer 字符串序列化器 RedisTemplate自动赋值为StringRedisSerializer对象
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        // RedisTemplate会自动初始化StringRedisSerializer，所以这里直接获取
        RedisSerializer stringRedisSerializer = redisTemplate.getStringSerializer();
        // 设置字符串序列化器，这样Spring就会把redis的key当做字符串处理
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        redisTemplate.setConnectionFactory(redisConnectionFactory(jedisPoolConfig()));
        return redisTemplate;
    }
}