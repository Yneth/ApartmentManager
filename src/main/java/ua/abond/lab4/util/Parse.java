package ua.abond.lab4.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class Parse {

    private Parse() {
    }

    public static Integer intObject(String str, Integer fallback) {
        Integer result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            result = fallback;
        }
        return result;
    }

    public static Integer intObject(String str) {
        return intObject(str, null);
    }

    public static int intValue(String str) {
        return intObject(str, 0);
    }

    public static int intValue(String str, int defaultValue) {
        return intObject(str, defaultValue);
    }

    public static Long longObject(String str) {
        Long result;
        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException e) {
            result = null;
        }
        return result;
    }

    public static <T extends Enum<T>> T enumeration(Class<T> type, String str, T defaultValue) {
        T result;
        try {
            result = Enum.valueOf(type, str.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            result = defaultValue;
        }
        return result;
    }

    public static Double doubleObject(String price) {
        Double result;
        try {
            result = Double.valueOf(price);
        } catch (NumberFormatException | NullPointerException e) {
            result = null;
        }
        return result;
    }

    public static BigDecimal bigDecimal(String price) {
        BigDecimal result;
        try {
            result = new BigDecimal(price);
        } catch (NumberFormatException | NullPointerException e) {
            result = null;
        }
        return result;
    }

    public static LocalDateTime localDateTime(String str, DateTimeFormatter formatter) {
        LocalDateTime result = null;
        try {
            result = LocalDateTime.parse(str, formatter);
        } catch (DateTimeParseException | NullPointerException e) {
            result = null;
        }
        return result;
    }
}
