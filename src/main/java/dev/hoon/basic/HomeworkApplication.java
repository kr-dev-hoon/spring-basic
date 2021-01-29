package dev.hoon.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = "dev.hoon.basic.domain.*.repository")
@ComponentScan(basePackages = { "dev.hoon.basic.domain.*.controller",
        "dev.hoon.basic.domain.*.service",
        "dev.hoon.basic.global.config" })
public class HomeworkApplication {

    public static void main(String[] args) {

        SpringApplication.run(HomeworkApplication.class, args);
    }

}
