package com.devops.h9pnxh;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldHTTP {
    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }
}
