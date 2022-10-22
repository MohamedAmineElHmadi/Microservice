package com.example.microserviceexam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient

public class MicroserviceExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceExamApplication.class, args);
    }

}
