package com.medcare.patient.service;

import com.medcare.patient.dto.PatientCreateRequest;
import com.medcare.patient.dto.PatientResponse;
import com.medcare.patient.exceptions.DuplicateMrnException;
import com.medcare.patient.exceptions.PatientNotFoundException;
import com.medcare.patient.mapper.PatientMapper;
import com.medcare.patient.model.Patient;
import com.medcare.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;

    @Override
    @Transactional
    public PatientResponse createPatient(PatientCreateRequest request) {
        if (patientRepository.existsByMrn(request.mrn())) {
            throw new DuplicateMrnException("MRN already exists: " + request.mrn());
        }
        if (request.email() != null && patientRepository.existsByEmail(request.email())) {
            throw new DuplicateMrnException("MRN already exists: " + request.mrn());
        }

        Patient patient = Patient.create(
                request.mrn(),
                request.firstName(),
                request.lastName(),
                request.dateOfBirth(),
                request.email(),
                request.phoneNumber()
        );

        Patient saved = patientRepository.save(patient);

        log.info("Patient successfully registered : id={}, mrn={}", saved.getId(), saved.getMrn());
        return patientMapper.toResponse(saved);
    }

    @Override
    public PatientResponse getPatientById(UUID id) {
        return patientRepository.findById(id)
                .map(patientMapper::toResponse)
                .orElseThrow(() -> new PatientNotFoundException("Patient with this id not found: " + id));
    }

    @Override
    public PatientResponse getPatientByMrn(String mrn) {
        return patientRepository.findByMrn(mrn)
                .map(patientMapper::toResponse)
                .orElseThrow(() -> new PatientNotFoundException("Patient with this Mrn not found: " + mrn));
    }

    @Override
    public void deactivatePatient(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with this Id is not found: " + id));
        patient.deactivate();
    }

    @Override
    public void markPatientDeceased(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient with this Id is not found: " + id));
        patient.markDeceased();
    }
}
