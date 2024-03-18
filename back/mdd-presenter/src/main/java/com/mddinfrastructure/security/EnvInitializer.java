package com.mddinfrastructure.security;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Application context initializer to load environment variables.
 * <p>
 * This class is used to load environment variables from a .env file
 * at the startup of the Spring Boot application. It implements {@link ApplicationContextInitializer}
 * to ensure these variables are loaded before the application context is initialized.
 * </p>
 */
public class EnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    /**
     * Loads environment variables and sets them as system properties.
     *
     * @param applicationContext The Spring Boot configurable application context.
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("spring.datasource.username", dotenv.get("DATABASE_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("DATABASE_PASSWORD"));
        System.setProperty("spring.datasource.url", dotenv.get("DATABASE_URL"));
    }
}
