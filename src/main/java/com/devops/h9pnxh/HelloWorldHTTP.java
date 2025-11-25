package com.devops.h9pnxh;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldHTTP {
    private final Name name;
    public HelloWorldHTTP(Name name) {
        this.name = name;
    }
    @GetMapping("/")
    // az eredeti Hello World kiegeszul egy neptunkoddal
    public String hello() {
        return "Hello World!" + " - " + name.name();
    }
}
