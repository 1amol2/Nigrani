package com.dev.nigrani.authentication.service;

import com.dev.nigrani.authentication.dtos.AuthResponse;
import com.dev.nigrani.authentication.dtos.LoginRequest;
import com.dev.nigrani.authentication.models.User;
import com.dev.nigrani.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository
                .findByOfficialId(request.getOfficialId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Invalid official ID or password"
                        )
                );

        if (!user.isActive()) {
            throw new IllegalArgumentException(
                    "Account is inactive"
            );
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new IllegalArgumentException(
                    "Invalid official ID or password"
            );
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.fromModel(user, token);
    }
}