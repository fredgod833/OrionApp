package com.mddinfrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.mdddetails.models"})
@EnableJpaRepositories(basePackages = {"com.mdddetails.repository"})
public class DBConfig {
}
