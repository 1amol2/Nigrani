package com.dev.nigrani.authentication.repository;

import com.dev.nigrani.authentication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByOfficialId(String officialId);
    boolean existsByOfficialId(String officialId);
}
