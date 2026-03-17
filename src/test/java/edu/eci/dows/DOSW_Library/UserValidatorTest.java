package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.model.User;
import edu.eci.dows.tdd.core.validator.UserValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserValidatorTest {

    @Test
    public void testIsValidWithCorrectUserReturnsTrue() {
        assertTrue(UserValidator.isValid(new User("Maria", "U1")));
    }

    @Test
    public void testIsValidWithNullUserReturnsFalse() {
        assertFalse(UserValidator.isValid(null));
    }

    @Test
    public void testIsValidWithMissingFieldsReturnsFalse() {
        assertFalse(UserValidator.isValid(new User("", "U1")));
        assertFalse(UserValidator.isValid(new User("Maria", "")));
        assertFalse(UserValidator.isValid(new User(null, "U1")));
    }
}

