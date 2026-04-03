package edu.eci.dows.tdd.persistence.norelational.adapter;

import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.persistence.norelational.document.UserDocument;
import edu.eci.dows.tdd.persistence.norelational.repository.UserMongoRepository;
import edu.eci.dows.tdd.persistence.port.UserRepositoryPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("mongo")
public class UserRepositoryMongoAdapter implements UserRepositoryPort {

    private final UserMongoRepository repo;

    public UserRepositoryMongoAdapter(UserMongoRepository repo) {
        this.repo = repo;
    }

    @Override
    public User save(User user) {
        UserDocument d = toDocument(user);
        UserDocument saved = repo.save(d);
        return toModel(saved);
    }

    @Override public List<User> findAll() { return repo.findAll().stream().map(this::toModel).toList(); }
    @Override public Optional<User> findById(String id) { return repo.findById(id).map(this::toModel); }
    @Override public void deleteById(String id) { repo.deleteById(id); }
    @Override public Optional<User> findByUsername(String username) { return repo.findByUsername(username).map(this::toModel); }
    @Override public boolean existsByUsername(String username) { return repo.findByUsername(username).isPresent(); }

    private UserDocument toDocument(User u) {
        UserDocument d = new UserDocument();
        d.setId(u.getId());
        d.setName(u.getName());
        d.setUsername(u.getUsername());
        d.setPassword(u.getPassword());
        d.setRole(u.getRole());

        d.setEmail(u.getEmail());
        d.setMembershipType(u.getMembershipType());
        d.setAddedAt(u.getAddedAt() != null ? u.getAddedAt() : Instant.now().toString());
        return d;
    }

    private User toModel(UserDocument d) {
        return new User(
                d.getId(),
                d.getName(),
                d.getUsername(),
                d.getPassword(),
                d.getRole(),
                d.getEmail(),
                d.getMembershipType(),
                d.getAddedAt()
        );
    }
}