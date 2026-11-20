package com.hopital.repository;

import com.hopital.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Accès aux données des entités Patient.
 */
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
