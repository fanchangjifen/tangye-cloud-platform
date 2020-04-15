package com.angel.dfs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.angel")
public class MyDfsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyDfsApplication.class, args);
    }
}

