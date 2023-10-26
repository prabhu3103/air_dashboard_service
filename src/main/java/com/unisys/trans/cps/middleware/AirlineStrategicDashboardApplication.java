package com.unisys.trans.cps.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class AirlineStrategicDashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirlineStrategicDashboardApplication.class, args);
    }
}
