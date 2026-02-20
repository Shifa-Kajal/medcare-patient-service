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
@NoArgsConstructor
@AllArgsConstructor
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
    @Setter(AccessLevel.NONE)
    private UUID id;

    @NotBlank
    @Size(max = 40)
    @Column(nullable = false, length = 40, unique = true, updatable = false)
    @Setter(AccessLevel.NONE)
    private String mrn;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String firstName;

    @NotBlank
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String lastName;

    @Email
    @Column(length = 120, unique = true)
    private String email;

    @Column(length = 30)
    private String phoneNumber;

    @PastOrPresent
    @NotNull
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status patientStatus = Status.ACTIVE;

    @Version
    @Setter(AccessLevel.NONE)
    private Long version;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
}
