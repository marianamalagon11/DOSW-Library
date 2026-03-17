package edu.eci.dows.tdd.core.util;

public class ValidationUtil {
    public static boolean validateId(String id) {
        return id != null && !id.trim().isEmpty();
    }
}
