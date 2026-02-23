package com.medcare.patient.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "patients",
indexes = {
        @Index(name = "idx_patients_mrn", columnList = "mrn", unique = true),
        @Index(name = "idx_patients_email", columnList = "email", unique = true)
})
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false, length = 40, unique = true, updatable = false)
    private String mrn;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(length = 120, unique = true)
    private String email;

    @Column(length = 30)
    private String phoneNumber;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PatientStatus patientStatus;

    @Version
    private Long version;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    public static Patient create(String mrn, String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber) {
        Patient patient = new Patient();
        patient.mrn = mrn;
        patient.firstName = firstName;
        patient.lastName = lastName;
        patient.dateOfBirth = dateOfBirth;
        patient.email = email;
        patient.phoneNumber = phoneNumber;
        patient.patientStatus = PatientStatus.ACTIVE;
        return patient;
    }


    public void deactivate(){
        if(this.patientStatus == PatientStatus.DECEASED) {
            throw new IllegalStateException("Cannot deactivate a deceased patient record");
        }
        this.patientStatus = PatientStatus.INACTIVE;
    }

    public void markDeceased(){
        this.patientStatus = PatientStatus.DECEASED;
    }

    public void reactivate(){
    if(this.patientStatus == PatientStatus.DECEASED) {
        throw new IllegalStateException("Cannot reactivate a deceased patient record");
    }
        this.patientStatus = PatientStatus.ACTIVE;
    }
}
