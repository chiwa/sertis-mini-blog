package com.sertis.miniblog.api.exception;

import lombok.Getter;

@Getter
public class AuthenticationException extends RuntimeException {

    private String developerMessage;

    public AuthenticationException(String errorMessage, String developerMessage) {
        super(errorMessage);
        this.developerMessage = developerMessage;
    }
}
