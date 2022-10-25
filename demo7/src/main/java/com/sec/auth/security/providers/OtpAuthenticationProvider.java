package com.sec.auth.security.providers;

import com.sec.auth.model.Otp;
import com.sec.auth.repository.OtpRepository;
import com.sec.auth.security.authentication.OtpAuthentication;
import com.sec.auth.security.authentication.UsernamePasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OtpAuthenticationProvider
implements AuthenticationProvider {

    @Autowired
    OtpRepository otpRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String otp = (String) authentication.getCredentials();

        Optional<Otp> o = otpRepository.findOtpByUsername(username);
        if(o.isPresent() && otp.equals(o.get().getOtp())) {
            List<GrantedAuthority> a = new ArrayList<>();
            a.add((GrantedAuthority) () -> "read");
            return new OtpAuthentication(username,otp, a);
        }
        return authentication;
        //throw new BadCredentialsException("Error !!");

    }


    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.equals(authentication);
    }
}
