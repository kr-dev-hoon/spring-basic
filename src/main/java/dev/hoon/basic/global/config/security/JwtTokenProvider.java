package dev.hoon.basic.global.config.security;

import dev.hoon.basic.domain.account.meta.Role;
import dev.hoon.basic.domain.account.service.AccountService;
import dev.hoon.basic.domain.account.service.RoleService;
import dev.hoon.basic.global.config.data.RedisClient;
import dev.hoon.basic.global.model.JwtExpired;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @NotNull
    @Value("spring.jwt.secret")
    private       String secretKey;
    private final String JWT_HEADER_KEY = "Authorization";
    private final long   EXPIRED_AT     = 1000 * 60 * 60; // 1 Hour

    private AccountService accountService;
    private RoleService    roleService;
    private RedisClient    redisClient;

    public JwtTokenProvider(AccountService accountService, RoleService roleService, RedisClient redisClient) {

        this.accountService = accountService;
        this.roleService = roleService;
        this.redisClient = redisClient;
    }

    public String resolvedToken(HttpServletRequest request) {

        return request.getHeader(JWT_HEADER_KEY);
    }

    public Claims getClaim(String token) {

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Authentication getAuthentication(String token) {

        String name = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getId();

        Function<Role, SimpleGrantedAuthority> grantFunc = role -> new SimpleGrantedAuthority(role.name());

        // TODO : 종속성 제거해야함.
        return accountService.findByName(name)
                .map(it -> new UsernamePasswordAuthenticationToken(
                        new User(it.getName(), it.getPassword(),
                                getRolesById(it.getName(), grantFunc)),
                        "", getRolesById(it.getName(), grantFunc))).get(); // Exception

    }

    public String generateToken(String id) {

        Claims claims = Jwts.claims();
        claims.put("roles", getRolesById(id, Enum::name));
        claims.put("iat", System.currentTimeMillis());

        return Jwts.builder()
                .setClaims(claims)
                .setId(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED_AT))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * redis black list에 token을 추가.
     */
    public void doExpiredToken(String token) throws AuthorizationServiceException {

        if (!isValidateToken(token)) {
            logger.debug("Logout Token is Invalid");
            throw new AuthorizationServiceException("Not Enable Token");
        }

        // TODO : REDIS의 BLACK LIST 저장 기한을 REDIS CACHE를 통해 관리해야함.
        redisClient.getEntityTemplate().opsForValue()
                .set(token, JwtExpired.builder().addedAt(LocalDateTime.now()).build());
    }

    /**
     * token의 expired 와 redis의 black list에 등록되었는지 여부를 확인
     *
     * @param token 조회 대상
     * @return Token 유효성
     */
    public boolean isValidateToken(String token) {

        try {
            return getClaim(token).getExpiration().after(new Date()) &&
                    redisClient.getEntityTemplate().opsForValue().get(token) == null; // logout된 상태인지를 확인.
        } catch (ExpiredJwtException e) {

            logger.warn("JWT Token Already Expired");

            return false;

        } catch (Exception e) {

            logger.warn("JWT Parser ERROR >> Message : {}", e.getMessage());

            return false;
        }

    }

    private <T> List<T> getRolesById(String id, Function<Role, T> mapper) {

        return roleService.findByAccountName(id).stream().map(mapper).collect(Collectors.toList());
    }

}