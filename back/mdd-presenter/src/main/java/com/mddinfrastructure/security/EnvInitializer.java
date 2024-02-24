package com.mddinfrastructure.security;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Initialisateur de contexte d'application pour charger les variables d'environnement.
 * <p>
 * Cette classe est utilisée pour charger les variables d'environnement depuis un fichier .env
 * au démarrage de l'application Spring Boot. Elle implémente {@link ApplicationContextInitializer}
 * pour garantir que ces variables soient chargées avant l'initialisation du contexte de l'application.
 * </p>
 */
public class EnvInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    /**
     * Charge les variables d'environnement et les définit comme propriétés système.
     *
     * @param applicationContext Le contexte d'application configurable de Spring Boot.
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("spring.datasource.username", dotenv.get("DATABASE_USERNAME"));
        System.setProperty("spring.datasource.password", dotenv.get("DATABASE_PASSWORD"));
        System.setProperty("spring.datasource.url", dotenv.get("DATABASE_URL"));
    }
}
