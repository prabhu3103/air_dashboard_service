package com.unisys.trans.cps.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


/**
 * @author Raji
 * @version V1.0
 * @since 31-AUG-23
 */
@SpringBootApplication
@EnableJpaRepositories
public class OperationalDashboardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperationalDashboardServiceApplication.class, args);
	}

}