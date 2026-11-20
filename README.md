# Gestion Consultations – Checkstyle

API Spring Boot de gestion de rendez-vous médicaux, utilisée pour mettre en place une analyse statique du code avec Checkstyle et des tests de métriques (complexité cyclomatique, maintenabilité).

**Stack :** Java 17, Spring Boot 3, Spring Data JPA, H2, Checkstyle, JUnit 5

## Lancer

```bash
mvn checkstyle:check          # analyse statique (échoue s'il y a des violations)
mvn checkstyle:checkstyle     # rapport HTML dans target/site/checkstyle.html
mvn test                      # tests de métriques
```

Le dossier `avant/` contient la version initiale du contrôleur et du service de stock, avant refactorisation.
