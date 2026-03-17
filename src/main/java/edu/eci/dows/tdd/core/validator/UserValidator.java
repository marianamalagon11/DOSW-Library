package edu.eci.dows.tdd.core.validator;

import edu.eci.dows.tdd.core.model.User;

public class UserValidator {
    public static boolean isValid(User user) {
        return user != null && user.getId() != null && !user.getId().isEmpty()
                && user.getName() != null && !user.getName().isEmpty();
    }
}
