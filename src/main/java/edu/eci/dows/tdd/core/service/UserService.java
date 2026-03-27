package edu.eci.dows.tdd.core.service;

import edu.eci.dows.tdd.controller.dto.CreateUserRequestDTO;
import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.relational.entity.UserEntity;
import edu.eci.dows.tdd.persistence.relational.entity.enums.UserRole;
import edu.eci.dows.tdd.persistence.relational.repository.UserRepository;
import edu.eci.dows.tdd.controller.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
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

        UserEntity entity = new UserEntity();
        entity.setId(request.getId());
        entity.setName(request.getName());
        entity.setUsername(request.getUsername());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setRole(UserRole.valueOf(request.getRole()));

        UserEntity saved = userRepository.save(entity);
        return UserMapper.toModel(saved);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::toModel)
                .toList();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id).map(UserMapper::toModel);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public Optional<UserEntity> findEntityByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}