package com.not_projesi.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.not_projesi"})
@EntityScan(basePackages = {"com.not_projesi"})
@EnableJpaRepositories(basePackages = {"com.not_projesi"})
@SpringBootApplication
public class NotProjesiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotProjesiApplication.class, args);
	}

}
