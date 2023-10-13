package com.openclassrooms.mddapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MddApiApplication {

	
	private static final Logger logger = LoggerFactory.getLogger(MddApiApplication.class);

	public static void main(String[] args) {
		logger.info("Application started!");

		SpringApplication.run(MddApiApplication.class, args);
	}

}
