package ru.spb.reshenie.monitoring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.spb.reshenie.monitoring.model.security.Role;
import ru.spb.reshenie.monitoring.model.security.User;
import ru.spb.reshenie.monitoring.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Created by vladimirsabo on 13.08.2023
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .formLogin(withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        Set<Role> roles = new HashSet<>(); //todo убрать генератор
        roles.add(Role.ADMIN);
        User user = new User(1L, passwordEncoder().encode("admin"), "admin", roles);
        userRepository.save(user);

        roles.clear();
        roles.add(Role.USER);
        User user2 = new User(2L, passwordEncoder().encode("user"), "user", roles);
        userRepository.save(user2);

        roles.clear();
        roles.add(Role.CLIENT);
        User user3 = new User(3L, passwordEncoder().encode("client"), "client", roles);
        userRepository.save(user3);

        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("no user with username ".concat(username)));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
