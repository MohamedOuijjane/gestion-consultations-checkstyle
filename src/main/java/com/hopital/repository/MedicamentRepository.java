package com.hopital.repository;

import com.hopital.entity.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Accès aux données des entités Medicament.
 */
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
}
