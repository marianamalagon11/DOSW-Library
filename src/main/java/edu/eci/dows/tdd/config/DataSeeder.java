package edu.eci.dows.tdd.config;

import edu.eci.dows.tdd.persistence.relational.entity.UserEntity;
import edu.eci.dows.tdd.persistence.relational.entity.enums.UserRole;
import edu.eci.dows.tdd.persistence.relational.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedUserIfMissing("lib-1", "Admin Librarian", "librarian", "librarian123", UserRole.LIBRARIAN);
        seedUserIfMissing("user-1", "Standard User", "user1", "user123", UserRole.USER);
    }

    private void seedUserIfMissing(String id, String name, String username, String rawPassword, UserRole role) {
        if (userRepository.existsByUsername(username)) {
            return;
        }
        UserEntity u = new UserEntity();
        u.setId(id);
        u.setName(name);
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setRole(role);
        userRepository.save(u);
    }
}