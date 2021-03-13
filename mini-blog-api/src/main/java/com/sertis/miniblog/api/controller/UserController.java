package com.sertis.miniblog.api.controller;


import com.sertis.miniblog.api.exception.AuthenticationException;
import com.sertis.miniblog.api.exception.DataNotFoundException;
import com.sertis.miniblog.api.model.request.AuthenticationRequest;
import com.sertis.miniblog.api.model.response.LoginResponse;
import com.sertis.miniblog.api.model.user.User;
import com.sertis.miniblog.api.repository.impl.UserServiceImpl;
import com.sertis.miniblog.api.security.JwtTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@Api(value="User", description="Api for manager users.")
public class UserController {

    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
    private UserServiceImpl userService;


    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "Ping/Pong API.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "pong")
    })
    @GetMapping(value = "/ping")
    public String ping(HttpServletRequest req){
        return "pong";
    }

    @ApiOperation(value = "Login, get user information and token.", response = LoginResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login Successful"),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @PostMapping(value = "/login")
    public LoginResponse login(@RequestBody AuthenticationRequest authenticationRequest) throws AuthenticationException {
        try {
            final User user = userService.findByUserName(authenticationRequest.getUsername());
            if (user == null) {
                log.error("User {} not found", authenticationRequest.getUsername());
                throw new AuthenticationException("User " + authenticationRequest.getUsername() + " not found.", null);
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()));

            final String token = jwtTokenService.generateToken(user.getUsername());
            return new LoginResponse(user, token);
        } catch (BadCredentialsException ex) {
            log.error(ex.getMessage());
            throw new AuthenticationException("Authentication failed, please check your username and password",
                    ex.getMessage());
        }
    }

    @ApiOperation(value = "Get user information from token (current login user).", response = LoginResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 204, message = "No content."),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @GetMapping(value = "/users")
    public User getLoggedUserData(HttpServletRequest req){
        try {
            final User user = jwtTokenService.getUserInformation(req);
            if (user == null) {
                log.error("User not found");
                throw new DataNotFoundException("User not found.", null);
            }
            return user;
        } catch (ExpiredJwtException ex) {
            log.error(ex.getMessage());
            throw new AuthenticationException("Token expired.",
                    ex.getMessage());
        }
    }
}
