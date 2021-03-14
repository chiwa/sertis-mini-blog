package com.sertis.miniblog.api.controller;

import com.sertis.miniblog.api.exception.AuthenticationException;
import com.sertis.miniblog.api.exception.UnexpectedException;
import com.sertis.miniblog.api.model.category.Category;
import com.sertis.miniblog.api.model.response.LoginResponse;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.reflections.Reflections.log;

@RestController
@Api(value="Category", description="Api for manager categories.")
public class CategoryController {

    private AuthenticationManager authenticationManager;
    private JwtTokenService jwtTokenService;
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
    public void setCategoryService(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }


    @ApiOperation(value = "Get all categories.", response = Category.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 204, message = "No content."),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @GetMapping(value = "/categories")
    public List<Category> getAllCategories(HttpServletRequest req){
        try {
           return categoryService.getAllCategory();
        } catch (ExpiredJwtException ex) {
            log.error(ex.getMessage());
            throw new AuthenticationException("Token expired.",
                    ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UnexpectedException(ex.getMessage());
        }
    }
}
