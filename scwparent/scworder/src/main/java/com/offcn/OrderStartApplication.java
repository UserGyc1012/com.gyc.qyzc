package com.offcn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName OrderStartApplication
 * @Deacription TODO
 * @Author 葛言超
 * @Date 2020/11/4 15:37
 * version:1.0 * 注意：本内容仅限于公司内部传阅，禁止外泄以及用于其他的商业目
 */

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.offcn.order.mapper")
@EnableFeignClients

@EnableCircuitBreaker
public class OrderStartApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderStartApplication.class);
    }
}
