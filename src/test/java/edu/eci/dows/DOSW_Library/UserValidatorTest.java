package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.validator.UserValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

    private User validUser() {
        User user = new User();
        user.setId("U1");
        user.setName("Maria");
        user.setUsername("maria");
        user.setPassword("secret");
        user.setRole("USER");
        return user;
    }

    @Test
    public void testIsValidWithCorrectUserReturnsTrue() {
        assertTrue(UserValidator.isValid(validUser()));
    }

    @Test
    public void testIsValidWithNullUserReturnsFalse() {
        assertFalse(UserValidator.isValid(null));
    }

    @Test
    public void testIsValidWithMissingFieldsReturnsFalse() {
        User missingId = validUser();
        missingId.setId("");
        User missingName = validUser();
        missingName.setName("");
        User missingUsername = validUser();
        missingUsername.setUsername("");
        User missingRole = validUser();
        missingRole.setRole("");

        assertFalse(UserValidator.isValid(missingId));
        assertFalse(UserValidator.isValid(missingName));
        assertFalse(UserValidator.isValid(missingUsername));
        assertFalse(UserValidator.isValid(missingRole));
    }
}

