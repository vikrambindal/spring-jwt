package com.vikram.service;

import com.vikram.controller.dto.TokenResponse;
import com.vikram.controller.dto.UserAccount;

public interface ApplicationService {

    TokenResponse registerUser(UserAccount userAccount);

    TokenResponse generateToken(UserAccount userAccount);
}
