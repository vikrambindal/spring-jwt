package com.vikram.controller;

import com.vikram.controller.dto.UserAccount;
import com.vikram.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping(value = "/greet", produces = "application/json")
    public UserAccount greetUser(@RequestHeader("Authorization") String bearerToken) {

        return applicationService.extractTokenInformation(bearerToken);

    }
}
