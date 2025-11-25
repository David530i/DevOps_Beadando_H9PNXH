package com.devops.h9pnxh;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldHTTP {
    @GetMapping("/")
    // az eredeti Hello World kiegeszul egy neptunkoddal
    public String hello() {
        return "Hello World!" + " - " + name();
    }

    // masik method, amit a hello() meghiv kiegeszitve a szoveget
    private String name() {
        return "H9PNXH";
    }

}
