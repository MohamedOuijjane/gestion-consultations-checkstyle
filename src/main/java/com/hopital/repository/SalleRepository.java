package com.hopital.repository;

import com.hopital.entity.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Accès aux données des entités Salle.
 */
public interface SalleRepository extends JpaRepository<Salle, Long> {
}
