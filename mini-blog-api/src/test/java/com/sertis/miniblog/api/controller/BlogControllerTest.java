package com.sertis.miniblog.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sertis.miniblog.api.model.blog.Blog;
import com.sertis.miniblog.api.model.request.AuthenticationRequest;
import com.sertis.miniblog.api.model.response.LoginResponse;
import com.sertis.miniblog.api.repository.impl.BlogServiceImpl;
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

import java.util.List;

import static org.junit.matchers.JUnitMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    private AuthenticationRequest authenticationRequest = new AuthenticationRequest();

    @Autowired
    private BlogController blogController;

    @Autowired
    private UserController userController;

    @Autowired
    private BlogServiceImpl blogService;

    private String header;

    @Before
    public void initial() throws Exception {
        blogController.setBlogService(blogService);
        this.header = generateHeader();
    }

    @Test
    public void get_all_blogs_test() throws Exception {

        this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
        MvcResult result = mockMvc.perform(get("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", header))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        List<Blog> blogList = objectMapper.readValue(content, new TypeReference<List<Blog>>() {
        });
        Assert.assertEquals(4, blogList.size());
        Assert.assertEquals("Install JDK", blogList.get(0).getTopic());
    }

    @Test
    public void get_all_blog_by_id_test() throws Exception {

        this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
        MvcResult result = mockMvc.perform(get("/blogs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", header))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Blog blog = objectMapper.readValue(content, Blog.class);
        Assert.assertEquals("Install JDK", blog.getTopic());
    }

    @Test
    public void get_blog_by_id_not_found_test() {
        try {
            this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
            MvcResult result = mockMvc.perform(get("/blogs/199")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("authorization", header))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("No blog id 199"));
        }
    }

    @Test
    public void create_new_blog_test() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
        MvcResult result = mockMvc.perform(post("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"topic\":\"test\", \"content\":\"test\", \"category_id\" : 1 }")
                .header("authorization", header))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Blog blog = objectMapper.readValue(content, Blog.class);
        Assert.assertEquals("test", blog.getTopic());
    }

    @Test
    public void update_blog_test() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
        MvcResult result = mockMvc.perform(put("/blogs/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"topic\":\"test edit\", \"content\":\"test edit\", \"category_id\" : 1}")
                .header("authorization", header))
                .andExpect(status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Blog blog = objectMapper.readValue(content, Blog.class);
        Assert.assertEquals("test edit", blog.getTopic());
    }

    @Test
    public void update_blog_other_user_test()  {
        try {
            this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
            MvcResult result = mockMvc.perform(put("/blogs/3")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{ \"topic\":\"test edit\", \"content\":\"test edit\", \"category_id\" : 1}")
                    .header("authorization", header))
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("You are not the owner of this blog"));
        }
    }

    @Test
    public void delete_blog_test() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
        MvcResult result = mockMvc.perform(post("/blogs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"topic\":\"for delete\", \"content\":\"for delete\", \"category_id\" : 1 }")
                .header("authorization", header))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        Assert.assertNotNull(content);
        Blog blog = objectMapper.readValue(content, Blog.class);

        result = mockMvc.perform(delete("/blogs/" + blog.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .header("authorization", header))
                .andExpect(status().isOk())
                .andReturn();
        content = result.getResponse().getContentAsString();
        Assert.assertTrue( objectMapper.readValue(content, Boolean.class));
    }

    @Test
    public void delete_other_user_blog_test() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
        try {
            MvcResult result = mockMvc.perform(delete("/blogs/3")
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("authorization", header))
                    .andReturn();
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("You are not the owner of this blog"));
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