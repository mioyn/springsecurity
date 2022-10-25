package com.sec.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/first")
public class FirstController {

    @RequestMapping("greeting")
    public String greeting(){
        return "hello";
    }

}
