package com.sec.auth.security.filters;

import com.sec.auth.model.Otp;
import com.sec.auth.repository.OtpRepository;
import com.sec.auth.security.authentication.OtpAuthentication;
import com.sec.auth.security.authentication.UsernamePasswordAuthentication;
import com.sec.auth.security.manager.TokenManager;
import com.sec.auth.security.providers.OtpAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    OtpRepository otpRepository;

    @Autowired
    private TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String usename = request.getHeader("username");
        String password = request.getHeader("password");
        String otp = request.getHeader("otp");

        if(otp == null) {
            Authentication a = new UsernamePasswordAuthentication(usename, password);
            a = authenticationManager.authenticate(a);

            String code = String.valueOf(new Random().nextInt(9999)+1000);
            Otp otpEntity = new Otp();
            otpEntity.setUsername(usename);
            otpEntity.setOtp(code);
            otpRepository.save(otpEntity);

        } else {
            Authentication a = new OtpAuthentication(usename, otp);
            a = authenticationManager.authenticate(a);
            String token =UUID.randomUUID().toString();
            tokenManager.add(token);
            response.setHeader("Authorization", token);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return !request.getServletPath().equals("/login");
    }
}
