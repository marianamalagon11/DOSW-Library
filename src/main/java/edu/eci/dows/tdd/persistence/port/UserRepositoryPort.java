package edu.eci.dows.tdd.persistence.port;

import edu.eci.dows.tdd.core.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    List<User> findAll();
    Optional<User> findById(String id);
    void deleteById(String id);

    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}