package com.sertis.miniblog.api.exception;

public class UnexpectedException extends RuntimeException {

    public UnexpectedException(String errorMessage) {
        super(errorMessage);
    }
}
