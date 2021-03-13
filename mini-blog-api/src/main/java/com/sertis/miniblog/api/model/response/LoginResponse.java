package com.sertis.miniblog.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

    public LoginResponse() {
        super();
    }
    public void LoginResponse(com.sertis.miniblog.api.model.user.User user, String token) {
        this.username = user.getUsername();
        this.token = token;
    }

    @JsonProperty("user_name")
    private String username;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("token")
    private String token;

    public  LoginResponse(com.sertis.miniblog.api.model.user.User user, String token) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.token = token;

    }
}
