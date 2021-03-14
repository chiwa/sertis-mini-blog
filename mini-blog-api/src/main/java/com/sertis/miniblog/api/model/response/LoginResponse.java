package com.sertis.miniblog.api.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sertis.miniblog.api.model.user.User;

public class LoginResponse {

    public LoginResponse() {
        super();
    }
    public void LoginResponse(User user, String token) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public  LoginResponse(User user, String token) {
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.token = token;

    }
}
