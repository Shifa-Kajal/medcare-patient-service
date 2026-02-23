package com.medcare.patient.exceptions;

public class DuplicateMrnException extends RuntimeException {
    public DuplicateMrnException(String message) {
        super(message);
    }
}
