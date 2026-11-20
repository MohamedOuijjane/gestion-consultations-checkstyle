package com.hopital.repository;

import com.hopital.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Accès aux données des entités RendezVous.
 */
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
}
