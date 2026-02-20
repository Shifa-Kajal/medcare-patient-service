package com.medcare.patient.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record PatientCreateRequest(
        @NotBlank
        @Size(max = 40)
        String mrn,

        @NotBlank
        @Size(max = 80)
        String firstName,

        @NotBlank
        @Size(max = 80)
        String lastName,

        @NotNull
        @PastOrPresent
        LocalDate dateOfBirth,

        @Email
        @Size(max = 120)
        String email,

        @Size(max = 30)
        String phoneNumber
) {
}
