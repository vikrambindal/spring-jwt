package com.vikram.service;

import com.vikram.domain.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JWTHelperService {

    String generateToken(UserEntity userEntity);

    String generateToken(Map<String, String> claimMap, String email);

    boolean isTokenValid(String jwtToken, UserDetails userDetails);

    Claims extractAllClaims(String jwtToken);

    <T> T verifyAndExtractClaims(String jwtToken, Function<Claims, T> function);
}
