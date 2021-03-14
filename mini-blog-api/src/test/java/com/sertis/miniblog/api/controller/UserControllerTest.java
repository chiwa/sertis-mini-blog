package com.sertis.miniblog.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sertis.miniblog.api.exception.AuthenticationException;
import com.sertis.miniblog.api.model.request.AuthenticationRequest;
import com.sertis.miniblog.api.model.response.LoginResponse;
import com.sertis.miniblog.api.repository.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertThrows;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    private AuthenticationRequest authenticationRequest = new AuthenticationRequest();

    @Autowired
    private UserController userController;

    @Autowired
    private UserServiceImpl userService;

    @Before
    public void initial() throws Exception {
        userController.setUserService(userService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void step1_ping_API_should_be_work_fine() throws Exception {
        MvcResult result = mockMvc.perform(get("/ping")
                .contentType("pain/text"))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertTrue(result.getResponse().getContentAsString().contains("pong"));
    }

    @Test
    public void step2_login_success() throws Exception {

       /* authenticationRequest.setUsername("chiwa");
        authenticationRequest.setPassword("123");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest)))
                //.andExpect(content().string(containsString("Authentication failed, please check your username and password")))
                .andExpect(status().isUnauthorized());*/

        authenticationRequest.setUsername("chiwa");
        authenticationRequest.setPassword("password");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")));
    }

    @Test
    public void step3_login_failed() {
        authenticationRequest.setUsername("chiwa");
        authenticationRequest.setPassword("123");
        try {
            mockMvc.perform(post("/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authenticationRequest)))
                    .andExpect(content().string(containsString("Authentication failed, please check your username and password")))
                    .andExpect(status().isUnauthorized());
        } catch (Exception e) {
        }
    }

    @Test
    public void step4_getLoggedUserInformationTest() throws Exception {
        authenticationRequest.setUsername("chiwa");
        authenticationRequest.setPassword("password");

        MvcResult result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andReturn();
        String content = result.getResponse().getContentAsString();

        LoginResponse loginResponse = objectMapper.readValue(content, LoginResponse.class);

        String header = "SERTIS " + loginResponse.getToken();
        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", header))
                .andExpect(status().isOk());
    }

    @Test
    public void step5_registerUserTest() throws Exception {
        MvcResult result = mockMvc.perform(post("/register-users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"username\":\"test\",\n" +
                        "\t\"password\":\"test\",\n" +
                        "\t\"first_name\" : \"test\",\n" +
                        "\t\"last_name\" : \"test\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertTrue(result.getResponse().getContentAsString().equalsIgnoreCase("{\"username\":\"test\",\"first_name\":\"test\",\"last_name\":\"test\"}"));
    }

}