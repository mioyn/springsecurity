package com.sec.auth.security.providers;

import com.sec.auth.security.authentication.UsernamePasswordAuthentication;
import com.sec.auth.service.JpaUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsernamePasswordAuthProvider
implements AuthenticationProvider{

    @Autowired
    private JpaUserDetailService jpaUserDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        UserDetails user = jpaUserDetailService.loadUserByUsername(username);

        if(passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthentication(username, password, user.getAuthorities());
        }

        throw new BadCredentialsException("Error!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthentication.class.equals(authentication);
    }
}
