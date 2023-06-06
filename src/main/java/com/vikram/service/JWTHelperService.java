package com.vikram.service;

import com.vikram.domain.UserEntity;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTHelperService {

    String extractUsername(String jwtToken);

    String generateToken(UserEntity userEntity);

    String generateToken(Map<String, String> claimMap, String email);

    boolean isTokenValid(String jwtToken, UserDetails userDetails);

    Claims extractAllClaims(String jwtToken);
}
