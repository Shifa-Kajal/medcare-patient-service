package com.medcare.patient.repository;

import com.medcare.patient.model.Patient;
import com.medcare.patient.model.PatientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByMrn(String mrn);

    boolean existsByMrn(String mrn);

    boolean existsByEmail(String email);

    Optional<Patient> findByEmail(String email);

    List<Patient> findByPatientStatus(PatientStatus status);
}
