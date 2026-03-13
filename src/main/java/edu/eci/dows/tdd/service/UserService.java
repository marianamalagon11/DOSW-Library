package edu.eci.dows.tdd.service;

import edu.eci.dows.tdd.model.User;
import java.util.*;

public class UserService {
    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }
}