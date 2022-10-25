package com.sec.auth.controller;

import com.sec.auth.model.User;
import com.sec.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/greeting")
    public String greeting(){
        return "hello";
    }

    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @PostMapping("add")
    public void addUser(@RequestBody User user){
        userRepository.save(user);
    }
}
