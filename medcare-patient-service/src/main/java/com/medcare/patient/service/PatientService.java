package com.medcare.patient.service;

import com.medcare.patient.dto.PatientCreateRequest;
import com.medcare.patient.dto.PatientResponse;

import java.util.UUID;

public interface PatientService {

    PatientResponse createPatient(PatientCreateRequest request);
    PatientResponse getPatientById(UUID id);
    PatientResponse getPatientByMrn(String mrn);
    void deactivatePatient(UUID id);
    void markPatientDeceased(UUID id);
}
