package com.hopital;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class QualityMetricsTest {

    private static final Path BASE = Path.of("src/main/java/com/hopital");
    private static final List<Path> FICHIERS = List.of(
            BASE.resolve("controller/ConsultationController.java"),
            BASE.resolve("service/ConsultationService.java"),
            BASE.resolve("service/StockService.java"));

    @Test
    void testComplexiteCyclomatique() throws IOException {
        for (Path fichier : FICHIERS) {
            int max = QualityMetrics.maxCyclomaticComplexity(fichier);
            System.out.println("Complexité cyclomatique max - " + fichier.getFileName() + " : " + max);
            assertTrue(max < 10, fichier.getFileName() + " a une méthode de complexité " + max);
        }
    }

    @Test
    void testMaintenabilite() throws IOException {
        for (Path fichier : FICHIERS) {
            double mi = QualityMetrics.maintainabilityIndex(fichier);
            System.out.printf("Indice de maintenabilité - %s : %.1f%n", fichier.getFileName(), mi);
            assertTrue(mi > 60, fichier.getFileName() + " a un indice de maintenabilité de " + mi);
        }
    }
}
