package com.sertis.miniblog.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiErrorResponse {

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("developer_message")
    private String developerMessage;

    public ApiErrorResponse(int statusCode, String errorMessage, String developerMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.developerMessage = developerMessage;
    }

}
