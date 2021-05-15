package com.springboot.chapter14.service.impl;

import com.alibaba.fastjson.JSON;
import com.springboot.chapter14.constant.Constant;
import com.springboot.chapter14.dao.ProductDao;
import com.springboot.chapter14.dao.PurchaseRecordDao;
import com.springboot.chapter14.pojo.Product;
import com.springboot.chapter14.pojo.PurchaseRecord;
import com.springboot.chapter14.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private PurchaseRecordDao purchaseRecordDao;
    @Autowired
    private StringRedisTemplate redisTemplate;
    private String purchaseScript =
            // 先将产品编号保存到集合中
            "redis.call('sadd',KEYS[1],ARGV[2]) \n" +
            // 购买列表
            "local productPurchaseList = KEYS[2]..ARGV[2] \n" +
            // 用户编号
            "local userId = ARGV[1] \n" +
            // 产品键
            "local product = 'product_'..ARGV[2] \n" +
            // 购买数量
            "local quantity = tonumber(ARGV[3]) \n" +
            // 当前库存
            "local stock = tonumber(redis.call('hget',product,'stock')) \n" +
            // 价格
            "local price = tonumber(redis.call('hget',product,'price')) \n" +
            // 购买时间
            "local purchase_date = ARGV[4] \n" +
            // 库存不足返回0
            "if stock < quantity then return 0 end \n" +
            // 扣减库存
            "stock = stock - quantity \n" +
            "redis.call('hset',product,'stock',tostring(stock)) \n" +
            // 计算价格
            "local sum = price * quantity \n" +
            // 合并购买记录数据
            "local purchaseRecord = userId..','..quantity..','..sum..','..price..','..purchase_date \n" +
            // 将购买记录保存到list里
            "redis.call('rpush',productPurchaseList,purchaseRecord) \n" +
            // 返回成功
            "return 1 \n";
    // 32位SHAl编码，第一次执行的时候先让Redis进行缓存脚本返回
    private String sha1;

    /**
     * 按时间重试
     * @param userId
     * @param productId
     * @param quantity
     * @return
     */
//    @Override
//    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
//    public boolean purchase(Long userId, Long productId, int quantity) {
//        // 当前时间
//        long start = System.currentTimeMillis();
//        while (true) {
//            // 循环时间
//            long end = System.currentTimeMillis();
//            // 如果循环时间大于100ms返回终止循环
//            if (end - start > 100) {
//                return false;
//            }
//            // 获取产品
//            Product product = productDao.selectByPrimaryKey(productId);
//            // 比较库存和购买数量
//            if (product.getStock() < quantity) {
//                // 库存不足
//                return false;
//            }
//            // 获取当前版本号
//            int version = product.getVersion();
//            // 扣减库存，同时将当前版本号发送给后台进行比较
//            int result = productDao.decreaseProduct(productId, quantity, version);
//            // 如果更新数据失败，说明数据在多线程中被其他线程修改
//            // 导致失败，则通过循环重入尝试购买商品
//            if (result == 0) {
//                continue;
//            }
//            // 初始化购买记录
//            PurchaseRecord purchaseRecord = initPurchaseRecord(userId, product, quantity);
//            // 插入购买记录
//            purchaseRecordDao.insert(purchaseRecord);
//            return true;
//        }
//    }

    /**
     * 按次数重试
     * @param userId
     * @param productId
     * @param quantity
     * @return
     */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public boolean purchase(Long userId, Long productId, int quantity) {
        // 限定循环5次
        for (int i = 0; i < 3; i++) {
            // 获取产品
            Product product = productDao.selectByPrimaryKey(productId);
            // 比较库存和购买数量
            if (product.getStock() < quantity) {
                // 库存不足
                return false;
            }
            // 获取当前版本号
            int version = product.getVersion();
            // 扣减库存，同时将当前版本号发送给后台进行比较
            int result = productDao.decreaseProductWithVersion(productId, quantity, version);
            // 如果更新数据失败，说明数据在多线程中被其他线程修改
            // 导致失败，则通过循环重入尝试购买商品
            if (result == 0) {
                continue;
            }
            // 初始化购买记录
            PurchaseRecord purchaseRecord = initPurchaseRecord(userId, product, quantity);
            // 插入购买记录
            purchaseRecordDao.insert(purchaseRecord);
            return true;
        }
        return false;
    }

    @Override
    public boolean purchaseRedis(Long userId, Long productId, int quantity) {
        // 购买时间
        Long purchaseDate = System.currentTimeMillis();
        Jedis jedis = null;
        try {
            // 获取原始连接
            jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();
            // 如果没有加载过，则先将脚本加载到Redis服务器，让其返回shal
            if (sha1 == null) {
                sha1 = jedis.scriptLoad(purchaseScript);
            }
            // 执行脚本，返回结果
            /**
             * 局部变量sha1代表32位的SHA1编码，用来执行缓存在Redis的脚本
             * 参数2代表将前面两个参数以键（key）的形式传递到脚本中，所以PRODUCT_SCHEDULE_ SET和PURCHASE_PRODUCT LIST都只是键，它们在Lua脚本中以KEYS[index］表示，而index则是它的索引，以1开始，例如，KEYS[1］表示第一个键。
             * 从第二个参数之后，则都是脚本的参数，在Lua脚本中会以ARGV[index］表示，同样以1开始，与KEYS [index］类似
             */
            Object res = jedis.evalsha(sha1, 2, Constant.PRODUCT_SCHEDULE_SET, Constant.PURCHASE_PRODUCT_LIST, userId + "", productId + "", quantity + "", purchaseDate + "");
            Long result = (Long) res;
            return result == 1;
        } finally {
            // 关闭jedis连接
            if (jedis != null && jedis.isConnected()) {
                jedis.close();
            }
        }
    }

    /**
     * 保存购买记录
     * @param purchaseRecords
     * @return
     */
    @Override
    // 当运行方法启用新的独立事务运行，在回滚时只会回滚这个方法内部的事务，而不会影响全局事务
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public boolean dealRedisPurchaser(List<PurchaseRecord> purchaseRecords) {
        for (PurchaseRecord pr : purchaseRecords) {
            purchaseRecordDao.insert(pr);
            productDao.decreaseProduct(pr.getProductId(), pr.getQuantity());
        }
        return true;
    }

    @Override
    public void addProductRedis(String productName, String stock, String price) {
        Map<String, Object> hash = new HashMap<String, Object>();
        hash.put("id", "6");
        hash.put("productName", productName);
        hash.put("stock", stock);
        hash.put("price", price);
        redisTemplate.opsForHash().putAll(Constant.PRODUCT_PREFIX + 6, hash);
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Product addProduct(String productName, String stock, String price) {
        Product product = new Product();
        product.setProductName(productName);
        product.setStock(Integer.parseInt(stock));
        product.setPrice(new BigDecimal(price));
        product.setVersion(0);
        productDao.insert(product);
        return product;
    }

    /**
     * 初始化购买信息
     * @param userId
     * @param product
     * @param quantity
     * @return
     */
    private PurchaseRecord initPurchaseRecord(Long userId, Product product, int quantity) {
        PurchaseRecord pr = new PurchaseRecord();
        pr.setPurchaseDate(new Date());
        pr.setPrice(product.getPrice());
        pr.setProductId(product.getId());
        pr.setQuantity(quantity);
        BigDecimal sum = product.getPrice().multiply(new BigDecimal(quantity));
        pr.setSum(sum);
        pr.setUserId(userId);
        return pr;
    }
}
