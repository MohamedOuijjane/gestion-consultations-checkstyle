package com.hopital.repository;

import com.hopital.entity.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Accès aux données des entités Medecin.
 */
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
}
