package com.dev.nigrani.authentication.dtos;

import com.dev.nigrani.authentication.models.User;
import lombok.*;

@Getter @Setter @NoArgsConstructor
public class LoginRequest {
    private String officialId;
    private String password;
    public User toModel() { return User.builder().officialId(officialId).password(password).build(); }
}
