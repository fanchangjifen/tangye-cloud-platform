package com.angel.dataway;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableHasor()
@EnableHasorWeb()
@ComponentScan("com.angel")
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class, scanBasePackages = {"com.angel.dataway"})
@EnableDiscoveryClient
public class DataWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataWayApplication.class, args);
    }
}
