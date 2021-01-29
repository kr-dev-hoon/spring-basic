package dev.hoon.basic.global.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi accountApi() {

        return GroupedOpenApi.builder()
                .pathsToMatch("/account/**")
                .group("account")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApi() {

        return GroupedOpenApi.builder()
                .pathsToMatch("/order/**")
                .group("order")
                .build();
    }

    @Bean
    public OpenAPI springOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("Spring Demo")
                        .description("Spring Demo Project - Backend Study")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

}
