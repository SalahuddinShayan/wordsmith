package com.wordsmith;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) ->
        authorizeHttpRequests
        .requestMatchers("/xyz").authenticated()
        .anyRequest().permitAll()
        ).csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

}
