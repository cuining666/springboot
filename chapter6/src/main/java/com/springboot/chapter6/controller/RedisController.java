package com.springboot.chapter6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.*;

@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * redis字符串和散列数据类型
     * @return
     */
    @RequestMapping("/stringAndHash")
    @ResponseBody
    public Map<String, Object> testStringAndHash() {
        redisTemplate.opsForValue().set("key1", "value1");
        // 注意这里使用JDK的序列化器，所以Redis保存时不是整数，不能运算
        redisTemplate.opsForValue().set("int_key", "1");
        stringRedisTemplate.opsForValue().set("int", "1");
        // 使用运算
        stringRedisTemplate.opsForValue().increment("int", 1);
        // 获取底层Jedis连接
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
        // 减1操作，这个命令RedisTemplate不支持，所以要先获取底层连接再操作
        jedis.decr("int");
        Map<String, Object> hash = new HashMap<String, Object>();
        hash.put("field1", "value1");
        hash.put("field2", "value2");
        // 存入一个散列数据类型
        stringRedisTemplate.opsForHash().putAll("hash", hash);
        // 新增一个字段
        stringRedisTemplate.opsForHash().put("hash", "field3", "value3");
        // 绑定散列操作的key，这样可以连续对同一个散列数据类型进行操作
        BoundHashOperations hashOps = stringRedisTemplate.boundHashOps("hash");
        // 删除两个字段
        hashOps.delete("field1", "field2");
        // 新增一个字段
        hashOps.put("field4", "value4");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    /**
     * redis列表数据类型
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Map<String, Object> testList() {
        // 插入两个列表，注意它们在链表的顺序
        // 链表从左到右顺序为vl0,v8,v6,v4,v2
        stringRedisTemplate.opsForList().leftPushAll("list1", "v2", "v4", "v6", "v8", "v10");
        // 链表从左到右顺序为vl,v2,v3,v4,v5,v6
        stringRedisTemplate.opsForList().rightPushAll("list2", "v1", "v2", "v3", "v4", "v5", "v6");
        // 绑定list2链表操作
        BoundListOperations listOps = stringRedisTemplate.boundListOps("list2");
        // 从右边弹出一个成员
        Object result1 = listOps.rightPop();
        // 获取定位元素，redis从0开始计算，这里值为v2
        Object result2 = listOps.index(1);
        // 从左边插入链表
        listOps.leftPush("v0");
        // 求链表长度
        Long size = listOps.size();
        // 求链表下标区间成员，整个链表下标范围为0到size-1，这里不去最后一个元素
        List elements = listOps.range(0, size - 2);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    /**
     * redis集合数据类型
     * @return
     */
    @RequestMapping("/set")
    @ResponseBody
    public Map<String, Object> testSet() {
        // 注意这里v1重复两次，因为集合不允许重复，所以只是插入5个成员到集合中
        stringRedisTemplate.opsForSet().add("set1", "v1", "v1", "v2", "v3", "v4", "v5");
        stringRedisTemplate.opsForSet().add("set2", "v2", "v4", "v6", "v8");
        // 绑定set1集合操作
        BoundSetOperations setOps = stringRedisTemplate.boundSetOps("set1");
        // 增加两个元素
        setOps.add("v6", "v7");
        // 删除两个元素
        setOps.remove("v1", "v7");
        // 返回所有元素
        Set set1 = setOps.members();
        // 求成员数
        Long size = setOps.size();
        // 求交集
        Set inter = setOps.intersect("set2");
        // 求交集，并且用新集合inter保存
        setOps.intersectAndStore("set2", "inter");
        // 求差集
        Set diff = setOps.diff("set2");
        // 求差集，并且用新集合diff保存
        setOps.diffAndStore("set2", "diff");
        // 求并集
        Set union = setOps.union("set2");
        // 求并集，并且用新集合union保存
        setOps.unionAndStore("set2", "union");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    @RequestMapping("/zset")
    @ResponseBody
    public Map<String, Object> testZset() {
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = new HashSet<>();
        for (int i = 1; i <= 9; i++) {
            // 分数
            double score = i * 0.1;
            // 创建一个TypedTuple对象，存入值和分数
            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<String>("value" + i, score);
            typedTupleSet.add(typedTuple);
        }
        // 往有序集合插入元素
        stringRedisTemplate.opsForZSet().add("zset1", typedTupleSet);
        // 绑定zset1有序集合操作
        BoundZSetOperations zsetOps = stringRedisTemplate.boundZSetOps("zset1");
        // 增加一个元素
        zsetOps.add("value10", 0.26);
        Set<String> setRange = zsetOps.range(1, 6);
        // 按分数排序获取有序集合
        Set<String> setScore = zsetOps.rangeByScore(0.2, 0.6);
        // 定义值范围
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        // 大于value3
        range.gt("value3");
        // 大于等于value3
//        range.gte("value3");
        // 小于value8
//        range.lt("value8");
        // 小于等于value8
        range.lte("value8");
        // 按值排序，请注意这个排序是按字符串排序
        Set<String> setLex = zsetOps.rangeByLex(range);
        // 删除元素
        zsetOps.remove("value9", "value2");
        // 求分数
        Double score = zsetOps.score("value8");
        // 在下标区间下，按分数排序，同时返回value和score
        Set<ZSetOperations.TypedTuple<String>> rangeSet = zsetOps.rangeWithScores(1, 6);
        // 在分数区间下，按分数排序，同时返回value和score
        Set<ZSetOperations.TypedTuple<String>> scoreSet = zsetOps.rangeByScoreWithScores(0.1, 0.6);
        // 按从大到小排序
        Set<String> reverseSet = zsetOps.reverseRange(2, 8);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    /**
     * redis事务机制
     * 1. 在调试的环境下让请求达到operations.exec()断点，此时在Redis上修改key1的值，然后再跳过断点，在请求完成后在Redis上
     * 查询key2和key3值，可以发现key2、key3返回的值都为空，因为程序中先使得Redis的watch命令监控了key1的值，而后的multi让
     * 之后的命令进入队列，而在exec方法运行前我们修改了key1，根据Redis事务的规则，它在exec方法后会探测key1是否被修改过，
     * 如果没有则会执行事务，否则就取消事务，所以key2和key3没有被保存到Redis服务器中
     *
     * 2. Redis事务和数据库事务的不一样，对于Redis事务是先让命令进入队列，所以一开始它并没有检测这个加一命令是否能够成功，
     * 只有在exec命令执行的时候，才能发现错误，对于出错的命令Redis只是报出错误，而错误后面的命令依旧被执行，
     * 所以key2和key3都存在数据，这就是Redis事务的特点，也是使用Redis事务需要特别注意的地方
     * @return
     */
    @RequestMapping("/multi")
    @ResponseBody
    public Map<String, Object> testMulti() {
        redisTemplate.opsForValue().set("key1", "value1");
        // 此处只是显示错误, 运行没事情
        List list = (List) redisTemplate.execute((RedisOperations operations) -> {
            // 设置要监控key1
            operations.watch("key1");
            // 开启事务，在exec命令执行前，全部都只是进入队列
            operations.multi();
            operations.opsForValue().set("key2", "value2");
            // 对key1进行加1操作，因为key1是个字符串，所以此处会报错
//            operations.opsForValue().increment("key1", 1);
            // 获取值将为null，因为redis只是把命令放入了队列
            Object value2 = operations.opsForValue().get("key2");
            System.out.println("命令在队列，所以value为null【" + value2 + "】");
            operations.opsForValue().set("key3", "value3");
            Object value3 = operations.opsForValue().get("key3");
            System.out.println("命令在队列，所以value为null【" + value3 + "】");
            // 执行exec命令，将先判别key1是否在监控后被修改过，如果是则不执行事务，否则就执行事务
            return operations.exec();
        });
        System.out.println(list);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    /**
     * redis流水线
     * 与事务一样，使用流水线的过程中，所有的命令也只是进入队列而没有执行，所以执行的命令返回值也为空，这也是需要注意的地方。
     * @return
     */
    @RequestMapping("/pipline")
    @ResponseBody
    public Map<String, Object> testPipline() {
        Long start = System.currentTimeMillis();
        // 沿用SessionCallback接口
        List list = redisTemplate.executePipelined((RedisOperations operations) -> {
            for (int i = 1; i <= 100000; i++) {
                operations.opsForValue().set("pipline_" + i, "value_" + i);
                String value = (String) operations.opsForValue().get("pipline" + i);
                if(i == 100000) {
                    System.out.println("命令只是进入队列，所以值为空【" + value + "】");
                }
            }
            return null;
        });
        Long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) + "毫秒。");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        return map;
    }

    /**
     * 执行简易Lua脚本
     * @return
     */
    @RequestMapping("/lua")
    @ResponseBody
    public Map<String, Object> testLua() {
        DefaultRedisScript redisScript = new DefaultRedisScript();
        // 设置脚本
        redisScript.setScriptText("return 'Hello Redis'");
        // 定义返回类型，注意：如果没有定义，Spring不会返回结果
        redisScript.setResultType(String.class);
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        // 执行Lua脚本
        String str = (String) redisTemplate.execute(redisScript, stringSerializer, stringSerializer, null);
        Map<String, Object> map = new HashMap<>();
        map.put("str", str);
        return map;
    }

    /**
     * 执行带有参数的Lua脚本
     * @return
     */
    @RequestMapping("/lua2")
    @ResponseBody
    public Map<String, Object> testLua2(String key1, String key2, String value1, String value2) {
        // 定义Lua脚本
        String lua = "redis.call('set', KEYS[1], ARGV[1]) \n"
                + "redis.call('set', KEYS[2], ARGV[2]) \n"
                + "local str1 = redis.call('get', KEYS[1]) \n"
                + "local str2 = redis.call('get', KEYS[2]) \n"
                + "if str1 == str2 then \n"
                + "return 1 \n"
                + "end \n"
                + "return 0 \n";
        System.out.println(lua);
        // 结果返回为Long
        DefaultRedisScript rs = new DefaultRedisScript();
        rs.setScriptText(lua);
        rs.setResultType(Long.class);
        // 采用字符串序列化器
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        // 定义key参数
        List<String> keyList = new ArrayList<>();
        keyList.add(key1);
        keyList.add(key2);
        // 传递两个参数值，其中第一个序列化器是key的序列化器，第二个序列化器是参数的序列化器
        Long result = (Long) redisTemplate.execute(rs, stringSerializer, stringSerializer, keyList, value1, value2);
        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        return map;
    }
}
