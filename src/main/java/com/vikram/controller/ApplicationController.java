package com.vikram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
public class ApplicationController {

    @GetMapping(value = "/greet", produces = "application/json")
    public String greetUser() {
        return "Hello World";
    }
}
