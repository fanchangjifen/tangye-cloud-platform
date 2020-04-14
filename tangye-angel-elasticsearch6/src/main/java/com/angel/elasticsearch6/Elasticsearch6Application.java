package com.angel.elasticsearch6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Elasticsearch6Application {
    public static void main(String[] args) {
        SpringApplication.run(Elasticsearch6Application.class, args);
    }
}
