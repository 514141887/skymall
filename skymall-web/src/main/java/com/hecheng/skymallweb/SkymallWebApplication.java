package com.hecheng.skymallweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SkymallWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkymallWebApplication.class, args);
    }

}
