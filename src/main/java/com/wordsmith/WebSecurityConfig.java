package com.wordsmith;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Bean
	SecurityFilterChain web(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authorizeHttpRequests) ->
        authorizeHttpRequests
        .requestMatchers("/novellist","/deletenovel","/chapterlist","/deletechapter","/messages").authenticated()
        .anyRequest().permitAll()
        ).csrf(AbstractHttpConfigurer::disable).formLogin(withDefaults());
        return http.build();
    }

}
