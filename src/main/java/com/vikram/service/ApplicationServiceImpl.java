package com.vikram.service;

import com.vikram.controller.dto.TokenResponse;
import com.vikram.controller.dto.UserAccount;
import com.vikram.controller.dto.UserResponse;
import com.vikram.domain.UserEntity;
import com.vikram.repository.UserEntityRepository;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final JWTHelperServiceImpl jwtHelperService;
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse registerUser(UserAccount userAccount) {

        UserEntity userEntity = userAccount.adaptToUserEntity();
        userEntity.setPassword(passwordEncoder.encode(userAccount.password()));
        userEntity.setRole(userAccount.role());
        userEntityRepository.save(userEntity);
        String jwtToken = jwtHelperService.generateToken(userEntity);
        return new TokenResponse(jwtToken);
    }

    @Override
    public TokenResponse generateToken(UserAccount userAccount) {

        Optional<UserEntity> userEntity =
                userEntityRepository.findByEmail(userAccount.email());
        if (userEntity.isEmpty() || !passwordEncoder.matches(userAccount.password(), userEntity.get().getPassword())) {
            throw new UsernameNotFoundException("Invalid user");
        }

        final var jwtToken = jwtHelperService.generateToken(userEntity.get());
        return new TokenResponse(jwtToken);
    }

    @Override
    public UserAccount extractTokenInformation(String jwtToken) {
        jwtToken = jwtToken.substring("Bearer ".length());
        String email = jwtHelperService.verifyAndExtractClaims(jwtToken, Claims::getSubject);
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(email);
        if (userEntityOptional.isEmpty()) {
            throw new UsernameNotFoundException("Invalid user");
        }

        return userEntityOptional.get().adaptToUserAccount();
    }

    @Override
    public List<UserResponse> getUsers() {
        List<UserEntity> userEntityList = userEntityRepository.findAll();
        return userEntityList.stream()
                .map(userEntity ->
                        new UserResponse(Strings.concat(userEntity.getFirstName(), userEntity.getLastName()),
                                userEntity.getEmail()))
                .toList();
    }

    @Override
    public Claims getAllClaims(String jwtToken) {
        return jwtHelperService.extractAllClaims(jwtToken);
    }
}
