package dev.hoon.basic.global.config.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        if (Objects.requireNonNull(HttpStatus.resolve(response.getStatus())).is2xxSuccessful()) {
            response.setStatus(response.getStatus());
        }

        response.sendError(response.getStatus(), "UnAuthorized");
    }
}