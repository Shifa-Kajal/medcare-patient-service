package com.medcare.patient.controller;

import com.medcare.patient.dto.PatientCreateRequest;
import com.medcare.patient.dto.PatientResponse;
import com.medcare.patient.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientCreateRequest request) {
        PatientResponse response = patientService.createPatient(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientResponse> getPatient(@PathVariable UUID id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @GetMapping("/mrn/{mrn}")
    public ResponseEntity<PatientResponse> getPatientByMrn(@PathVariable String mrn) {
        return ResponseEntity.ok(patientService.getPatientByMrn(mrn));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<PatientResponse> deactivatePatient(@PathVariable UUID id) {
        patientService.deactivatePatient(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/deceased")
    public ResponseEntity<PatientResponse> markDeceased(@PathVariable UUID id) {
        patientService.markPatientDeceased(id);
        return ResponseEntity.noContent().build();
    }

}
