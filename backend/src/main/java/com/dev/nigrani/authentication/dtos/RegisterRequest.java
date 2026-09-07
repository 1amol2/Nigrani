package com.dev.nigrani.authentication.dtos;

import com.dev.nigrani.authentication.models.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String officialId;
    private String password;
    private UserRole role;
    public User toModel() {
        return User.builder().name(name).officialId(officialId).password(password)
                .role(role == null ? UserRole.OFFICER : role).active(true).build();
    }
}
