package com.dev.nigrani.authentication.service;

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

    @Override
    public User register(User user) {
        if (isBlank(user.getName()) || isBlank(user.getOfficialId()) || isBlank(user.getPassword())) {
            throw new IllegalArgumentException("Name, official ID, and password are required");
        }
        if (userRepository.existsByOfficialId(user.getOfficialId())) {
            throw new IllegalArgumentException("Official ID already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User login(User loginUser) {
        User user = userRepository.findByOfficialId(loginUser.getOfficialId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid official ID or password"));
        if (!user.isActive() || !passwordEncoder.matches(loginUser.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid official ID or password");
        }
        return user;
    }

    private boolean isBlank(String value) { return value == null || value.trim().isEmpty(); }
}
