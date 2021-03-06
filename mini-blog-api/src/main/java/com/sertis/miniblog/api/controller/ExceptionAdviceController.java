package com.sertis.miniblog.api.controller;

import com.sertis.miniblog.api.exception.*;
import com.sertis.miniblog.api.model.response.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdviceController {

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleAuthenticationException(AuthenticationException ex) {
        return new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), ex.getDeveloperMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiErrorResponse handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null);
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiErrorResponse handleDataNotFoundException(DataNotFoundException ex) {
        return new ApiErrorResponse(HttpStatus.NO_CONTENT.value(), ex.getMessage(), null);
    }

    @ExceptionHandler(DatabaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleDatabaseException(DatabaseException ex) {
        return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getDeveloperMessage());
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleInvalidDataException(InvalidDataException ex) {
        return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ex.getDeveloperMessage());
    }

    @ExceptionHandler(UnexpectedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleDuplicatedDataException(UnexpectedException ex) {
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value() , ex.getMessage(), ex.getMessage());
    }

}
