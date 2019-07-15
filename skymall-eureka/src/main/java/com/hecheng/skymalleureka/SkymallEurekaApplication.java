package com.hecheng.skymalleureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SkymallEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkymallEurekaApplication.class, args);
    }

}
