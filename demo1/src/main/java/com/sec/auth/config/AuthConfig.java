package com.sec.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AuthConfig {

    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager ser = new InMemoryUserDetailsManager();
        UserDetails userDetails = User.withUsername("Midhu")
                .password("12340")
                .authorities("read")
                .build();

        ser.createUser(userDetails);
        return ser;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
