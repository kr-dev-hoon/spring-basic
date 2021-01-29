package dev.hoon.basic.global.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@RedisHash("jwtExpired")
@Getter
@Builder
@AllArgsConstructor
public class JwtExpired implements Serializable {

    private static final long serialVersionUID = -6473613268167162594L;

    private LocalDateTime addedAt;
}
