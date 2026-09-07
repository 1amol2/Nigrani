package com.dev.nigrani.authentication.dtos;

import com.dev.nigrani.authentication.models.User;
import lombok.*;

@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {
    private String token;
    private UserResponse user;
    public static AuthResponse fromModel(User user, String token) {
        return AuthResponse.builder().token(token).user(UserResponse.fromModel(user)).build();
    }
}
