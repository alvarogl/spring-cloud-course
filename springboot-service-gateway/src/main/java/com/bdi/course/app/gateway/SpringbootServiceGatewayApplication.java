package com.bdi.course.app.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringbootServiceGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootServiceGatewayApplication.class, args);
    }

}
