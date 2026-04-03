package edu.eci.dows.tdd.core.service;

import edu.eci.dows.tdd.controller.dto.CreateUserRequestDTO;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.port.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepositoryPort userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(CreateUserRequestDTO request) {
        if (request == null
                || request.getId() == null || request.getId().isBlank()
                || request.getName() == null || request.getName().isBlank()
                || request.getUsername() == null || request.getUsername().isBlank()
                || request.getPassword() == null || request.getPassword().isBlank()
                || request.getRole() == null || request.getRole().isBlank()) {
            throw new IllegalArgumentException("Invalid user payload");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User u = new User(
                request.getId(),
                request.getName(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole(),
                request.getEmail(),
                request.getMembershipType(),
                request.getAddedAt() != null ? request.getAddedAt() : Instant.now().toString()
        );

        return userRepository.save(u);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}