package ua.abond.lab4.util;

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

    public static Long longInt(String str) {
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
            result = Enum.valueOf(type, str);
        } catch (IllegalArgumentException | NullPointerException e) {
            result = defaultValue;
        }
        return result;
    }
}
