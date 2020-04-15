package com.angel.presto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.angel")
@SpringBootApplication
@EnableDiscoveryClient
public class PrestoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrestoApplication.class, args);
    }
}
