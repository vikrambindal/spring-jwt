package com.vikram.service;

import com.vikram.controller.dto.TokenResponse;
import com.vikram.controller.dto.UserAccount;
import com.vikram.controller.dto.UserResponse;
import io.jsonwebtoken.Claims;

import java.util.List;

public interface ApplicationService {

    TokenResponse registerUser(UserAccount userAccount);

    TokenResponse generateToken(UserAccount userAccount);

    UserAccount extractTokenInformation(String jwtToken);

    List<UserResponse> getUsers();

    Claims getAllClaims(String jwtToken);
}
