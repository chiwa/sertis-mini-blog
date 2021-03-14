package com.sertis.miniblog.api.controller;

import com.sertis.miniblog.api.exception.AuthenticationException;
import com.sertis.miniblog.api.exception.DataNotFoundException;
import com.sertis.miniblog.api.exception.InvalidDataException;
import com.sertis.miniblog.api.exception.UnexpectedException;
import com.sertis.miniblog.api.model.blog.Blog;
import com.sertis.miniblog.api.model.request.BlogRequest;
import com.sertis.miniblog.api.model.user.User;
import com.sertis.miniblog.api.repository.impl.BlogServiceImpl;
import com.sertis.miniblog.api.repository.impl.CategoryServiceImpl;
import com.sertis.miniblog.api.security.JwtTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.reflections.Reflections.log;

@RestController
@Api(value="Category", description="Api for manager blog.")
public class BlogController {

    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
    private BlogServiceImpl blogService;
    private CategoryServiceImpl categoryService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Autowired
    public void setBlogService(BlogServiceImpl blogService) {
        this.blogService = blogService;
    }

    @Autowired
    public void setCategoryService(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "Get blog by id.", response = Blog.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 204, message = "No content."),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @GetMapping(value = "/blogs/{id}")
    public Blog getBlogById(HttpServletRequest req, @PathVariable("id") Integer id){
        try {
           Blog result =  blogService.findById(id);
           if (result == null) {
               throw new DataNotFoundException("No blog id " + id, null);
           }
           return result;
        } catch (ExpiredJwtException ex) {
            log.error(ex.getMessage());
            throw new AuthenticationException("Token expired.",
                    ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UnexpectedException(ex.getMessage());
        }
    }

    @ApiOperation(value = "Get all blogs.", response = Blog.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 204, message = "No content."),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @GetMapping(value = "/blogs")
    public List<Blog> getAllBlogs(HttpServletRequest req){
        try {
            return blogService.getAllBlogs();
        } catch (ExpiredJwtException ex) {
            log.error(ex.getMessage());
            throw new AuthenticationException("Token expired.",
                    ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UnexpectedException(ex.getMessage());
        }
    }

    @ApiOperation(value = "Create new blog.", response = Blog.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Bad request,  Invalid data."),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @PostMapping("/blogs")
    public Blog addNewBlog(HttpServletRequest req, @RequestBody BlogRequest blogRequest){
        try {
            final User user = jwtTokenService.getUserInformation(req);
            if (user == null) {
                log.error("User not found");
                throw new DataNotFoundException("User not found.", null);
            }
            if (blogRequest == null || blogRequest.getTopic().isEmpty() || blogRequest.getContent().isEmpty() ) {
                throw new InvalidDataException("Topic or Content can not empty.", null);
            }
            Blog blog = new Blog();
            blog.setTopic(blogRequest.getTopic());
            blog.setContent(blogRequest.getContent());
            blog.setUser(user);
            blog.setCategory(categoryService.findById(blogRequest.getCategoryId()));
            blogService.save(blog);
            return blog;
        } catch (ExpiredJwtException ex) {
            log.error(ex.getMessage());
            throw new AuthenticationException("Token expired.",
                    ex.getMessage());
        }  catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UnexpectedException(ex.getMessage());
        }
    }

    @ApiOperation(value = "Create new blog.", response = Blog.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Bad request,  Invalid data or user."),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @PutMapping("/blogs/{id}")
    public Blog updateBlog(HttpServletRequest req, @RequestBody BlogRequest blogRequest, @PathVariable("id") Integer id){
        try {
            final User user = jwtTokenService.getUserInformation(req);
            if (user == null) {
                log.error("User not found");
                throw new DataNotFoundException("User not found.", null);
            }
            if (blogRequest == null || blogRequest.getTopic().isEmpty() || blogRequest.getContent().isEmpty()) {
                throw new InvalidDataException("Topic or Content can not empty.", null);
            }
            Blog blog = blogService.findById(id);
            if (blog == null) {
                throw new DataNotFoundException("There is no blog id " + id, null);
            }
            if (!user.getUsername().equals(blog.getUser().getUsername())) {
                throw new InvalidDataException("You are not the owner of this blog" , null);
            }
            blog.setTopic(blogRequest.getTopic());
            blog.setContent(blogRequest.getContent());
            blog.setUser(user);
            blog.setCategory(categoryService.findById(blogRequest.getCategoryId()));
            blogService.save(blog);
            return blog;
        } catch (ExpiredJwtException ex) {
            log.error(ex.getMessage());
            throw new AuthenticationException("Token expired.",
                    ex.getMessage());
        }  catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UnexpectedException(ex.getMessage());
        }
    }
}
