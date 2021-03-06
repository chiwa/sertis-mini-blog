package com.sertis.miniblog.api.exception;

public class AuthenticationException extends RuntimeException {

    private String developerMessage;

    public AuthenticationException(String errorMessage, String developerMessage) {
        super(errorMessage);
        this.developerMessage = developerMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }
}
