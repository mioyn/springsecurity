package com.sec.auth.service;

import com.sec.auth.security.model.SecurityUser;
import com.sec.auth.model.User;
import com.sec.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JpaUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usr = userRepository.findUserByUserName(username);
        User user = usr.orElseThrow(()-> new UsernameNotFoundException("Error!"));
        return new SecurityUser(user);
    }
}
