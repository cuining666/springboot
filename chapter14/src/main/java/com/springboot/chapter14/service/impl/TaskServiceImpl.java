package com.springboot.chapter14.service.impl;

import com.springboot.chapter14.constant.Constant;
import com.springboot.chapter14.pojo.PurchaseRecord;
import com.springboot.chapter14.service.PurchaseService;
import com.springboot.chapter14.service.TaskService;
import org.apache.ibatis.javassist.bytecode.annotation.LongMemberValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private PurchaseService purchaseService;

    @Override
    @Scheduled(cron = "0 0/3 23 * * ?")
    public void purchaseTask() {
        System.out.println("定时任务开始...");
        Set<String> productIdList = redisTemplate.opsForSet().members(Constant.PRODUCT_SCHEDULE_SET);
        List<PurchaseRecord> purchaseRecordList = new ArrayList<>();
        for (String productIdStr : productIdList) {
            Long productId = Long.parseLong(productIdStr);
            String purchaseKey = Constant.PURCHASE_PRODUCT_LIST + productId;
            BoundListOperations<String, String> ops = redisTemplate.boundListOps(purchaseKey);
            long size = redisTemplate.opsForList().size(purchaseKey);
            Long times = size % Constant.ONE_TIME_SIZE == 0 ? size / Constant.ONE_TIME_SIZE : size / Constant.ONE_TIME_SIZE + 1;
            for (int i = 0; i < times; i++) {
                // 获取至多TIME SIZE个抢红包信息
                List<String> prList = null;
                if (i == 0) {
                    prList = ops.range(i * Constant.ONE_TIME_SIZE, (i + 1) * Constant.ONE_TIME_SIZE);
                } else {
                    prList = ops.range(i * Constant.ONE_TIME_SIZE + 1, (i + 1) * Constant.ONE_TIME_SIZE);
                }
                for (String prStr : prList) {
                    PurchaseRecord pr = createPurchaseRecord(productId, prStr);
                    purchaseRecordList.add(pr);
                }
                try {
                    // 该方法采用新建事务的方式，不会导致全局事务回滚
                    purchaseService.dealRedisPurchaser(purchaseRecordList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                // 清除列表为空，等待重新写入数据
                purchaseRecordList.clear();
            }
            // 删除购买列表
            redisTemplate.delete(purchaseKey);
            // 从商品集合中删除商品
            redisTemplate.opsForSet().remove(Constant.PRODUCT_SCHEDULE_SET, productIdStr);
            System.out.println("定时任务结束...");
        }
    }

    private PurchaseRecord createPurchaseRecord(Long productId, String prStr) {
        PurchaseRecord purchaseRecord = new PurchaseRecord();
        String[] arr = prStr.split(",");
        purchaseRecord.setUserId(Long.parseLong(arr[0]));
        purchaseRecord.setQuantity(Integer.parseInt(arr[1]));
        purchaseRecord.setSum(new BigDecimal(arr[2]));
        purchaseRecord.setProductId(productId);
        purchaseRecord.setPrice(new BigDecimal(arr[3]));
        Long time = Long.parseLong(arr[4]);
        Timestamp purchaseTime = new Timestamp(time);
        purchaseRecord.setPurchaseDate(purchaseTime);
        return  purchaseRecord;
    }
}
