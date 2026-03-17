package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.util.ValidationUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationUtilTest {

    @Test
    public void testValidateIdWithValidValueReturnsTrue() {
        assertTrue(ValidationUtil.validateId("B001"));
    }

    @Test
    public void testValidateIdWithNullOrBlankReturnsFalse() {
        assertFalse(ValidationUtil.validateId(null));
        assertFalse(ValidationUtil.validateId(""));
        assertFalse(ValidationUtil.validateId("   "));
    }
}

