package com.sertis.miniblog.api.exception;


public class InvalidDataException extends RuntimeException {

    private String developerMessage;

    public InvalidDataException(String errorMessage, String developerMessage) {
        super(errorMessage);
        this.developerMessage = developerMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }
}
