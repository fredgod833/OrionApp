package com.openclassrooms.mddapi;

import com.openclassrooms.mddapi.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class MddApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MddApiApplication.class, args);
	}

}
