package com.sec.auth.config;

import com.sec.auth.model.User;
import com.sec.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

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
