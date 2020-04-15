package com.angel.elasticsearch7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Elasticsearch7Application {
    public static void main(String[] args) {
        SpringApplication.run(Elasticsearch7Application.class, args);
    }
}
