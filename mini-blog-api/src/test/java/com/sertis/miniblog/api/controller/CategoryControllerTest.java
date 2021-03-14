package com.sertis.miniblog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sertis.miniblog.api.model.request.AuthenticationRequest;
import com.sertis.miniblog.api.model.response.LoginResponse;
import com.sertis.miniblog.api.repository.impl.CategoryServiceImpl;
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

import static org.junit.matchers.JUnitMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    private AuthenticationRequest authenticationRequest = new AuthenticationRequest();

    @Autowired
    private CategoryController categoryController;

    @Autowired
    private UserController userController;

    @Autowired
    private CategoryServiceImpl categoryService;

   private String header;

    @Before
    public void initial() throws Exception {
        categoryController.setCategoryService(categoryService);
        this.header = generateHeader();
    }

    @Test
    public void get_all_categories_test() throws Exception {

        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        MvcResult result = mockMvc.perform(get("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", header))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Assert.assertEquals("[{\"id\":1,\"category_name\":\"Travel\"},{\"id\":2,\"category_name\":\"Programming\"},{\"id\":3,\"category_name\":\"Foods\"}]", content);
    }

    @Test
    public void get_category_by_id_success() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
        MvcResult result = mockMvc.perform(get("/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", header))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Assert.assertEquals("{\"id\":1,\"category_name\":\"Travel\"}", content);
    }

    @Test
    public void get_category_by_id_not_found()  {
        try {
            this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
            MvcResult  result = mockMvc.perform(get("/categories/199")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("authorization", header))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("There is no category id 199"));
        }
    }

    private String generateHeader() throws Exception {
        authenticationRequest.setUsername("chiwa");
        authenticationRequest.setPassword("password");
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MvcResult result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("token")))
                .andReturn();
        String content = result.getResponse().getContentAsString();

        LoginResponse loginResponse = objectMapper.readValue(content, LoginResponse.class);

        return "SERTIS " + loginResponse.getToken();
    }
}