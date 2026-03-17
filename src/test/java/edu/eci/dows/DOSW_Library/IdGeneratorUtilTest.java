package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.util.IdGeneratorUtil;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class IdGeneratorUtilTest {

    @Test
    public void testGenerateIdReturnsValidUuid() {
        String id = IdGeneratorUtil.generateId();

        assertNotNull(id);
        assertDoesNotThrow(() -> UUID.fromString(id));
    }

    @Test
    public void testGenerateIdReturnsDifferentValuesOnConsecutiveCalls() {
        String id1 = IdGeneratorUtil.generateId();
        String id2 = IdGeneratorUtil.generateId();

        assertNotEquals(id1, id2);
    }
}

