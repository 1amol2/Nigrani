package com.dev.nigrani.config;

import com.dev.nigrani.authentication.models.User;
import com.dev.nigrani.authentication.models.UserRole;
import com.dev.nigrani.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        String officialId = "NIGRANI001";

        if (userRepository.existsByOfficialId(officialId)) {
            return;
        }

        User officer = User.builder()
                .name("Nigrani Demo Officer")
                .officialId(officialId)
                .password(passwordEncoder.encode("Nigrani@123"))
                .role(UserRole.OFFICER)
                .active(true)
                .build();

        userRepository.save(officer);

        System.out.println("========================================");
        System.out.println("Nigrani demo user created");
        System.out.println("Official ID: NIGRANI001");
        System.out.println("Password: Nigrani@123");
        System.out.println("========================================");
    }
}