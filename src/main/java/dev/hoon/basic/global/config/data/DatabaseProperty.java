package dev.hoon.basic.global.config.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("spring.datasource")
public class DatabaseProperty {

    private String url;
    private Sub    sub;
    private String username;
    private String password;
    private String driverClassName;

    @Getter
    @Setter
    public static class Sub {

        private String name;
        private String url;
    }
}