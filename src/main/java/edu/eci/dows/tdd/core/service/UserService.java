package edu.eci.dows.tdd.core.service;

import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.repository.UserRepository;
import edu.eci.dows.tdd.persistence.entity.UserEntity;
import edu.eci.dows.tdd.controller.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addUser(User user) {
        UserEntity entity = UserMapper.toEntity(user);
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
}