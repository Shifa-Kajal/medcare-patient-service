package com.medcare.patient.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @Setter(AccessLevel.NONE)
    private UUID id;

    @NotBlank
    @Size(max = 40)
    @Column(nullable = false, length = 40, unique = true)
    @Setter(AccessLevel.NONE)
    private String mrn;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(length = 120, unique = true)
    private String email;

    @Column(length = 30)
    private String phoneNumber;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status patientStatus;

}
