package com.thomazcm.financeira.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hello")
@RestController
public class HelloApi {
    
    @GetMapping
    public String helloWorld() {
        return "hello world!";
    }

}
