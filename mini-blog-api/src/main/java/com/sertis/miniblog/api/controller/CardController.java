package com.sertis.miniblog.api.controller;

import com.sertis.miniblog.api.exception.AuthenticationException;
import com.sertis.miniblog.api.exception.DataNotFoundException;
import com.sertis.miniblog.api.exception.InvalidDataException;
import com.sertis.miniblog.api.exception.UnexpectedException;
import com.sertis.miniblog.api.model.card.Card;
import com.sertis.miniblog.api.model.request.CardRequest;
import com.sertis.miniblog.api.model.user.User;
import com.sertis.miniblog.api.repository.impl.CardServiceImpl;
import com.sertis.miniblog.api.repository.impl.CategoryServiceImpl;
import com.sertis.miniblog.api.security.JwtTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.reflections.Reflections.log;

@RestController
@Api(value = "Cards", description = "Api for manager blog.")
@CrossOrigin
public class CardController {

    private JwtTokenService jwtTokenService;
    private CardServiceImpl cardService;
    private CategoryServiceImpl categoryService;

    @Autowired
    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Autowired
    public void setCardService(CardServiceImpl cardService) {
        this.cardService = cardService;
    }

    @Autowired
    public void setCategoryService(CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

    @ApiOperation(value = "Get blog by id.", response = Card.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 204, message = "No content."),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @GetMapping(value = "/cards/{id}")
    public Card getCardById(HttpServletRequest req, @PathVariable("id") Integer id) {
        try {
            Card result = cardService.findById(id);
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

    @ApiOperation(value = "Get all cards.", response = Card.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 204, message = "No content."),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @GetMapping(value = "/cards")
    public List<Card> getAllCards(HttpServletRequest req) {
        try {
            return cardService.getAllCards();
        } catch (ExpiredJwtException ex) {
            log.error(ex.getMessage());
            throw new AuthenticationException("Token expired.",
                    ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UnexpectedException(ex.getMessage());
        }
    }

    @ApiOperation(value = "Create new blog.", response = Card.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Bad request,  Invalid data."),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @PostMapping("/cards")
    public Card addNewCard(HttpServletRequest req, @RequestBody CardRequest cardRequest) {
        try {
            final User user = jwtTokenService.getUserInformation(req);
            if (user == null) {
                log.error("User not found");
                throw new DataNotFoundException("User not found.", null);
            }
            if (cardRequest == null || cardRequest.getTopic().isEmpty() || cardRequest.getContent().isEmpty()) {
                throw new InvalidDataException("Topic or Content can not empty.", null);
            }
            Card card = new Card();
            card.setTopic(cardRequest.getTopic());
            card.setContent(cardRequest.getContent());
            card.setUser(user);
            card.setCategory(categoryService.findById(cardRequest.getCategoryId()));
            cardService.save(card);
            return card;
        } catch (ExpiredJwtException ex) {
            log.error(ex.getMessage());
            throw new AuthenticationException("Token expired.",
                    ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UnexpectedException(ex.getMessage());
        }
    }

    @ApiOperation(value = "Update blog.", response = Card.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Bad request,  Invalid data or user."),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @PutMapping("/cards/{id}")
    public Card updateCard(HttpServletRequest req, @RequestBody CardRequest cardRequest, @PathVariable("id") Integer id) {
        try {
            final User user = jwtTokenService.getUserInformation(req);
            if (user == null) {
                log.error("User not found");
                throw new DataNotFoundException("User not found.", null);
            }
            if (cardRequest == null || cardRequest.getTopic().isEmpty() || cardRequest.getContent().isEmpty()) {
                throw new InvalidDataException("Topic or Content can not empty.", null);
            }
            Card card = cardService.findById(id);
            if (card == null) {
                throw new DataNotFoundException("There is no blog id " + id, null);
            }
            if (!user.getUsername().equals(card.getUser().getUsername())) {
                throw new InvalidDataException("You are not the owner of this blog", null);
            }
            card.setTopic(cardRequest.getTopic());
            card.setContent(cardRequest.getContent());
            card.setUser(user);
            card.setCategory(categoryService.findById(cardRequest.getCategoryId()));
            cardService.save(card);
            return card;
        } catch (ExpiredJwtException ex) {
            log.error(ex.getMessage());
            throw new AuthenticationException("Token expired.",
                    ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new UnexpectedException(ex.getMessage());
        }
    }

    @ApiOperation(value = "Delete blog.", response = Boolean.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 400, message = "Bad request,  Invalid data or user."),
            @ApiResponse(code = 401, message = "Authentication failed."),
            @ApiResponse(code = 500, message = "Unexpected exception.")
    })
    @DeleteMapping("/cards/{id}")
    public Boolean deleteCard(HttpServletRequest req, @PathVariable("id") Integer id) {
        try {
            final User user = jwtTokenService.getUserInformation(req);
            if (user == null) {
                log.error("User not found");
                throw new DataNotFoundException("User not found.", null);
            }
            Card card = cardService.findById(id);
            if (card == null) {
                throw new DataNotFoundException("There is no card id " + id, null);
            }
            if (!user.getUsername().equals(card.getUser().getUsername())) {
                throw new InvalidDataException("You are not the owner of this card", null);
            }
            cardService.deleteById(id);
            return true;
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
