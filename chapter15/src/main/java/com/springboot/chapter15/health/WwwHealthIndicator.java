package com.springboot.chapter15.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import java.net.InetAddress;

/**
 * 监测服务器是否能够访问万维网
 */
@Component
public class WwwHealthIndicator extends AbstractHealthIndicator {

    // 通过监测百度服务器，看能否访问互联网
    private static final String BAI_DU = "www.baidu.com";
    // 超时时间
    private static final int TIME_OUT = 3000;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        boolean status = ping();
        if (status) {
            // 健康指标为可用状态，并添加一个消息项
            builder.withDetail("message", "当前服务器可以访问万维网").up();
        } else {
            // 健康指标为不再提供服务，并添加一个消息项
            builder.withDetail("message", "当前无法访问万维网").outOfService();
        }
    }

    /**
     * 监测百度服务器能够访问，用以判断能否访问万维网
     * @return
     */
    private boolean ping() {
        try {
            return InetAddress.getByName(BAI_DU).isReachable(TIME_OUT);
        } catch (Exception e) {
            return false;
        }
    }
}
