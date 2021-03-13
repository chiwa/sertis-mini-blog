package com.sertis.miniblog.api.security;


import com.sertis.miniblog.api.configuration.JwtConfiguration;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.reflections.Reflections.log;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenService jwtTokenService;

    private JwtConfiguration jwtConfiguration;

    @Autowired
    public void setJwtConfiguration(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws IOException, ServletException {

        String header = request.getHeader(jwtConfiguration.getHeader());
        if (header != null && header.startsWith(jwtConfiguration.getPrefix())) {
            String token = header.replace(jwtConfiguration.getPrefix(),"");
            try {
                String username = jwtTokenService.getUsernameFromToken(token);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtTokenService.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
                        );
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        logger.info("authenticated user " + username + ", setting security context");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (IllegalArgumentException ex) {
                log.error("Can not get username from token");
                response.setHeader(jwtConfiguration.getError(), "Can not get username from token");
            } catch (ExpiredJwtException ex) {
                log.error("Token expired");
                response.setHeader(jwtConfiguration.getError(), "Token expired");
            } catch(UsernameNotFoundException e){
                log.error("User not found.");
                response.setHeader(jwtConfiguration.getError(), "User not found.");
            }
        }
        chain.doFilter(request, response);
    }
}