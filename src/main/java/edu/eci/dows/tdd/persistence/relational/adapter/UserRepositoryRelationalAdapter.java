package edu.eci.dows.tdd.persistence.relational.adapter;

import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.port.UserRepositoryPort;
import edu.eci.dows.tdd.persistence.relational.entity.UserEntity;
import edu.eci.dows.tdd.persistence.relational.entity.enums.UserRole;
import edu.eci.dows.tdd.persistence.relational.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("relational")
public class UserRepositoryRelationalAdapter implements UserRepositoryPort {

    private final UserRepository repo;

    public UserRepositoryRelationalAdapter(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User save(User user) {
        UserEntity e = new UserEntity();
        e.setId(user.getId());
        e.setName(user.getName());
        e.setUsername(user.getUsername());
        e.setPassword(user.getPassword());
        if (user.getRole() != null) e.setRole(UserRole.valueOf(user.getRole()));

        UserEntity saved = repo.save(e);
        return toModel(saved);
    }

    @Override public List<User> findAll() { return repo.findAll().stream().map(this::toModel).toList(); }
    @Override public Optional<User> findById(String id) { return repo.findById(id).map(this::toModel); }
    @Override public void deleteById(String id) { repo.deleteById(id); }
    @Override public Optional<User> findByUsername(String username) { return repo.findByUsername(username).map(this::toModel); }
    @Override public boolean existsByUsername(String username) { return repo.existsByUsername(username); }

    private User toModel(UserEntity e) {
        return new User(
                e.getId(),
                e.getName(),
                e.getUsername(),
                e.getPassword(),
                e.getRole() == null ? null : e.getRole().name(),
                null,
                null,
                null
        );
    }
}