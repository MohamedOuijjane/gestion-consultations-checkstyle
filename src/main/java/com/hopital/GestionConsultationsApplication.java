package com.hopital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Point d'entrée de l'application GestionConsultations.
 */
@SpringBootApplication
public class GestionConsultationsApplication {

    /**
     * Démarre l'application.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(GestionConsultationsApplication.class, args);
    }
}
