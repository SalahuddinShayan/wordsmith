package com.wordsmith;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	
	    
	    
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/dashboard").hasAnyAuthority("ADMIN", "TRANSLATOR", "EDITOR")
                .requestMatchers("/admin/**","/novellist","/deletenovel","/chapterlist","/deletechapter","/messages","/allchapters","/auth/users", "/comments/allcomments","/deletemessage", "/actuator", "/actuator/*").hasAnyAuthority("ADMIN")
                .requestMatchers("/auth/register", "/auth/verify-otp", "/auth/verify","/auth/forgot-password","/auth/verify-reset-otp","/auth/reset-password").permitAll()
                .anyRequest().permitAll() // Allow unrestricted access to other pages
            )
            .formLogin(login -> login
            	    .loginPage("/auth/loginpage")
            	    .permitAll()
            	)
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                
            );
        return http.build();
    }
	
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	

}
