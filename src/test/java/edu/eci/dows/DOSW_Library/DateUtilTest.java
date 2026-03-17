package edu.eci.dows.DOSW_Library;

import edu.eci.dows.tdd.core.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilTest {

    @Test
    public void testIsPastDateWithPastDateReturnsTrue() {
        assertTrue(DateUtil.isPastDate(LocalDate.now().minusDays(1)));
    }

    @Test
    public void testIsPastDateWithTodayFutureOrNullReturnsFalse() {
        assertFalse(DateUtil.isPastDate(LocalDate.now()));
        assertFalse(DateUtil.isPastDate(LocalDate.now().plusDays(1)));
        assertFalse(DateUtil.isPastDate(null));
    }
}

