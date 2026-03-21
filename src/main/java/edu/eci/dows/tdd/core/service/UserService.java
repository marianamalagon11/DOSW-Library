package edu.eci.dows.tdd.core.service;

import edu.eci.dows.tdd.core.model.User;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Data
public class UserService {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getAllUsers() {
        return users;
    }

    public User getUserById(String id) {
        return users.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst().orElse(null);
    }

    public void deleteUser(String id) {
        users.removeIf(u -> u.getId().equals(id));
    }
}