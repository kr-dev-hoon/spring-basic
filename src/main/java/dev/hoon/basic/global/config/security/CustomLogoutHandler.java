package dev.hoon.basic.global.config.security;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtTokenProvider jwtTokenProvider;

    public CustomLogoutHandler(JwtTokenProvider jwtTokenProvider) {

        this.jwtTokenProvider = jwtTokenProvider;
    }

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        try {

            Optional.ofNullable(jwtTokenProvider.resolvedToken(request))
                    .ifPresent(jwtTokenProvider::doExpiredToken);

            response.sendError(HttpStatus.OK.value());

        } catch (Exception e) {
            response.sendError(HttpStatus.UNAUTHORIZED.value());
        }

    }
}
