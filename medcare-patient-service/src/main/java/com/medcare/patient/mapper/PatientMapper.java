package com.medcare.patient.mapper;

import com.medcare.patient.dto.PatientResponse;
import com.medcare.patient.model.Patient;
import com.medcare.patient.model.PatientStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "status", source = "patientStatus")
    PatientResponse toResponse(Patient patient);

    default String mapStatus(PatientStatus status) {
        return status == null ? null : status.name();
    }
}
