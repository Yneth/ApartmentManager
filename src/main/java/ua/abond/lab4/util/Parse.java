package ua.abond.lab4.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public final class Parse {

    private Parse() {
    }

    public static Integer integer(String str, Integer fallback) {
        Integer result;
        try {
            result = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            result = fallback;
        }
        return result;
    }

    public static Integer integer(String str) {
        return integer(str, null);
    }

    public static int integerPrimitive(String str) {
        return integer(str, 0);
    }

    public static int integerPrimitive(String str, int defaultValue) {
        return integer(str, defaultValue);
    }

    public static Long longValue(String str) {
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

    public static Double doubleValue(String price) {
        Double result;
        try {
            result = Double.valueOf(price);
        } catch (NumberFormatException | NullPointerException e) {
            result = null;
        }
        return result;
    }

    public static BigDecimal bigDecimal(String price) {
        return Optional.ofNullable(Parse.doubleValue(price)).
                map(BigDecimal::new).
                orElse(null);
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
