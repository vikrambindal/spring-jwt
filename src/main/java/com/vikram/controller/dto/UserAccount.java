package com.vikram.controller.dto;

import com.vikram.domain.Role;
import com.vikram.domain.UserEntity;

public record UserAccount(String firstName, String lastName, String email, String password, Role role) {

    public UserEntity adaptToUserEntity() {
        return UserEntity.builder()
                .email(email())
                .firstName(firstName())
                .lastName(lastName())
                .password(password())
                .build();
    }
}
