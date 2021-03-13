package com.sertis.miniblog.api.exception;

public class DatabaseException extends RuntimeException {

    private String developerMessage;

    public DatabaseException(String errorMessage, String developerMessage) {
        super(errorMessage);
        this.developerMessage = developerMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }
}
