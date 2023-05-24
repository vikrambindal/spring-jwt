package com.vikram.service;

import com.vikram.controller.dto.TokenResponse;
import com.vikram.controller.dto.UserAccount;
import com.vikram.domain.Role;
import com.vikram.domain.UserEntity;
import com.vikram.repository.UserEntityRepository;
import com.vikram.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final JwtService jwtService;
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TokenResponse registerUser(UserAccount userAccount) {

        UserEntity userEntity = userAccount.adaptToUserEntity();
        userEntity.setPassword(passwordEncoder.encode(userAccount.password()));
        userEntity.setRole(Role.USER);
        userEntityRepository.save(userEntity);
        String jwtToken = jwtService.generateToken(userAccount.email());
        return new TokenResponse(jwtToken);
    }

    @Override
    public TokenResponse generateToken(UserAccount userAccount) {

        Optional<UserEntity> userEntity =
                userEntityRepository.findByEmail(userAccount.email());
        if (userEntity.isEmpty() || !passwordEncoder.matches(userAccount.password(), userEntity.get().getPassword())) {
            throw new UsernameNotFoundException("Invalid user");
        }

        final var jwtToken = jwtService.generateToken(userEntity.get().getEmail());
        return new TokenResponse(jwtToken);
    }
}
