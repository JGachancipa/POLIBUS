package com.politecnico.polibus.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.politecnico.polibus.backend")
public class GpsInitializer {
    public static void main(String[] args) {
        SpringApplication.run(GpsInitializer.class, args);
    }
}
