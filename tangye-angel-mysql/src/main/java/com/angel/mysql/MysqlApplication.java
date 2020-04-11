package com.angel.mysql;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.angel")
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class MysqlApplication {
    public static void main(String[] args) {
        SpringApplication.run(MysqlApplication.class, args);
    }
}
