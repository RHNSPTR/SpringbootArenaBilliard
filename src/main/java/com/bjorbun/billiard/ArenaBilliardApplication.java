package com.bjorbun.billiard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.spring.annotation.EnableVaadin;

@SpringBootApplication

@EnableVaadin({"com.bjorbun.billiard.view", "com.bjorbun.billiard"}) 
public class ArenaBilliardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArenaBilliardApplication.class, args);
    }
}