package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.util.DateUtil;
import edu.eci.dows.tdd.core.util.IdGeneratorUtil;
import edu.eci.dows.tdd.core.util.ValidationUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UtilConstructorsTest {

    @Test
    public void testUtilityClassesCanBeInstantiated() {
        assertNotNull(new DateUtil());
        assertNotNull(new ValidationUtil());
        assertNotNull(new IdGeneratorUtil());
    }
}

