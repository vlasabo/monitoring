package ru.spb.reshenie.monitoring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spb.reshenie.monitoring.model.security.User;

import java.util.Optional;

/**
 * Created by vladimirsabo on 13.08.2023
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

}
