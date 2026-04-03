package edu.eci.dows.tdd;

import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.port.UserRepositoryPort;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        seedUserIfMissing(
                "librarian_001",
                "Admin Librarian",
                "librarian",
                "12345",
                "LIBRARIAN",
                "librarian@demo.com",
                "VIP"
        );

        seedUserIfMissing(
                "user_001",
                "Demo User",
                "user",
                "12345",
                "USER",
                "user@demo.com",
                "standard"
        );
    }

    private void seedUserIfMissing(
            String id,
            String name,
            String username,
            String rawPassword,
            String role,
            String email,
            String membershipType
    ) {
        if (userRepository.existsByUsername(username)) {
            return;
        }

        User u = new User(
                id,
                name,
                username,
                passwordEncoder.encode(rawPassword),
                role,
                email,
                membershipType,
                Instant.now().toString()
        );

        userRepository.save(u);
    }
}