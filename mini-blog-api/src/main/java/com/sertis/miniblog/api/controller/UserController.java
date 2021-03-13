package com.sertis.miniblog.api.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class UserController {

    @ApiOperation(value = "Ping/Pong API.", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "pong")
    })
    @GetMapping(value = "/ping")
    public String ping(HttpServletRequest req){
        return "pong";
    }


}
