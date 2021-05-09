package com.springboot.chapter12.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl {

    private int count1 = 1;
    private int count2 = 1;
    private int count3 = 1;
    private int count4 = 1;

    // 每隔一秒执行一次
    @Scheduled(fixedRate = 1000)
    // 使用异步执行
    @Async
    public void job1() {
        System.out.println("【" + Thread.currentThread().getName() + "】" + "【job1】每秒执行一次，执行第【" + count1 + "】次");
        count1 ++;
    }

    @Scheduled(fixedRate = 1000)
    @Async
    public void job2() {
        System.out.println("【" + Thread.currentThread().getName() + "】" + "【job2】每秒执行一次，执行第【" + count2 + "】次");
        count2 ++;
    }

    // Spring IoC容器初始化后，第一次延迟3秒，每隔1秒执行一次
    @Scheduled(initialDelay = 3000, fixedRate = 1000)
    @Async
    public void job3() {
        System.out.println("【" + Thread.currentThread().getName() + "】" + "【job3】每秒执行一次，执行第【" + count3 + "】次");
        count3 ++;
    }

    /**
     * * 表示任意值
     * ? 不指定值，用于处理天和星期配置的冲突
     * - 指定时间区间
     * / 指定时间间隔执行
     * L 最后的
     * # 第几个
     * , 列举多个项
     * cron有6～7 个空格分隔的时间元素，按顺序依次是“秒分时天月星期年”，其中年是一个可以不配置的元素，例如下面的配置：
     * 0 0 0 ? * WED
     *
     * "0 0 0 * * ?" 每天0点整触发
     * "0 15 23 ? * *" 每天23:15触发
     * "0 15 0 * * ?" 每天0:15触发
     * "0 15 10 * * ? *" 每天早上10:15触发
     * "0 30 10 * * ? 2018" 2018年的每天早上10:30触发
     * "0 * 23 * * ?" 每天从23点开始到23点59分每分钟一次触发
     * "0 0/3 23 * * ?" 每天从23点开始到23:59分结束每3min一次触发
     * "0 0/3 20,23 * * ?" 每天的20点至20:59和23点至23点59分两个时间段内每3min一次触发
     * "0 0-5 21 * * ?" 每天21:00至21:05每分钟一次触发
     * "0 10,44 14 ? 3 WED" 3月的每周三的14:10和14:44触发
     * "0 0 23 ? * MON-FRI" 每周一到周五的23:00触发
     * "0 30 23 ? * 6L 2017-2020" 2017年至2020年的每月最后一个周五的23:30触发
     * "0 15 22 ? * 6#3" 每月第三周周五的22:15触发
     */
    // 11:00到11:59点每分钟执行一次
    @Scheduled(cron = "0 * 11 * * ?")
    @Async
    public void job4() {
        System.out.println("【" + Thread.currentThread().getName() + "】" + "【job4】每秒执行一次，执行第【" + count4 + "】次");
        count4 ++;
    }
}
