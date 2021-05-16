package com.springboot.chapter15.endpoint;

import org.omg.CORBA.ObjectHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义数据库监测端点
 * 在Actuator中加入端点只需要加入注解＠Endpoint即可，这个注解会同时提供JMX监控和Web监控。
 * 如果只想提供JMX监控，可以使用注解＠JmxEndpoint；如果只想提供Web监控，可以使用注解＠WebEndpoint。
 * 正如上述内容所示，Actuator还会存在默认的端点，也可以使用＠WebEndpointExtension或者@EndpointJmxExtension对己有的端点进行扩展。
 */
@Component
// 端点id, 是否在默认情况下启用端点
@Endpoint(id = "dbcheck", enableByDefault = true)
public class DatabaseConnectionEndpoint {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    // 一个端点只能存在一个＠ReadOperation标注的方法
    // 它代表的是HTTP的GET请求
    @ReadOperation
    public Map<String, Object> check() {
        Connection conn = null;
        Map<String, Object> msgMap = new HashMap<>();
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(url, username, password);
            msgMap.put("success", true);
            msgMap.put("message", "测试数据库连接成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return msgMap;
    }
}
