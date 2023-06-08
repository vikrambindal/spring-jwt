package com.vikram.controller;

import com.vikram.controller.dto.TokenResponse;
import com.vikram.controller.dto.UserAccount;
import com.vikram.service.ApplicationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account/v1")
@AllArgsConstructor
@Slf4j
public class ManagementController {

    private final ApplicationService applicationService;

    @PostMapping("/register")
    public TokenResponse registerUser(@RequestBody UserAccount userAccount) {
        log.info("Registering User REQ {}", userAccount);

        return applicationService.registerUser(userAccount);
    }

    @PostMapping("/generate")
    public TokenResponse generateToken(@RequestBody UserAccount userAccount) {

        log.info("Generating Token REQ {}", userAccount);

        return applicationService.generateToken(userAccount);
    }
}
