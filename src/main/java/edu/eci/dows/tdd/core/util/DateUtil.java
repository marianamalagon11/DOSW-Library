package edu.eci.dows.tdd.core.util;

import java.time.LocalDate;

public class DateUtil {
    public static boolean isPastDate(LocalDate date) {
        return date != null && date.isBefore(LocalDate.now());
    }
}
