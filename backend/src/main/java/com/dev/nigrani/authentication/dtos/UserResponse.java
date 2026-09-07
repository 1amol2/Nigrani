package com.dev.nigrani.authentication.dtos;

import com.dev.nigrani.authentication.models.*;
import lombok.*;

@Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private Long id;
    private String name;
    private String officialId;
    private UserRole role;
    public static UserResponse fromModel(User user) {
        return UserResponse.builder().id(user.getId()).name(user.getName())
                .officialId(user.getOfficialId()).role(user.getRole()).build();
    }
}
