package com.vikram.controller;

import com.vikram.controller.dto.GreetResponse;
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
    public GreetResponse greetUser(@RequestHeader("Authorization") String bearerToken) {

        UserAccount userAccount = applicationService.extractTokenInformation(bearerToken);
        return new GreetResponse(String.format("Welcome %s %s", userAccount.firstName(), userAccount.lastName()));

    }
}
