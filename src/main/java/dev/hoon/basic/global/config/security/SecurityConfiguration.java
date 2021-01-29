package dev.hoon.basic.global.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtTokenProvider               jwtTokenProvider;

    public SecurityConfiguration(
            CustomAuthenticationEntryPoint customAuthenticationEntryPoint, JwtTokenProvider jwtTokenProvider) {

        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void configure(WebSecurity security) throws Exception {

        security.ignoring().antMatchers("/v3/api-docs/*", "/swagger-ui/*");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/sign-up", "/sign-in").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .logoutUrl("/logout")
                .addLogoutHandler(new CustomLogoutHandler(jwtTokenProvider))
                .and()
                .addFilterAfter(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class);

    }
}
