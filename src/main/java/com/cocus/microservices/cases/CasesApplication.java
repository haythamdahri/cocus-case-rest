package com.cocus.microservices.cases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.cocus.microservices.bo", "com.cocus.microservices.cases"})
@EnableJpaRepositories(value = {"com.cocus.microservices.cases.repositories"})
@EnableFeignClients
public class CasesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasesApplication.class, args);
	}

}
