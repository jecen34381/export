package com.example.mq1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Mq1Application {

    public static void main(String[] args) {
        SpringApplication.run(Mq1Application.class, args);
    }

}
