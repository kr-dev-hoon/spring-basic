package dev.hoon.basic.global.config.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {

        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Optional.ofNullable(jwtTokenProvider.resolvedToken((HttpServletRequest) request))
                .filter(jwtTokenProvider::isValidateToken)
                .ifPresent(it -> SecurityContextHolder.getContext().setAuthentication(
                        jwtTokenProvider.getAuthentication(it)));

        chain.doFilter(request, response);
    }

}