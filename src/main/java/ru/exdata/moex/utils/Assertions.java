package ru.exdata.moex.utils;

import jakarta.validation.constraints.NotNull;

import java.util.Collection;
import java.util.function.Supplier;

public class Assertions {

    @NotNull
    public static <T, E extends Exception> T isNotNull(T value, Supplier<E> exceptionSupplier) throws E {
        if (value != null) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    @NotNull
    public static <T, E extends Exception> T[] isSingle(T[] value, Supplier<E> exceptionSupplier) throws E {
        if (value != null && value.length == 1) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    @NotNull
    public static <T, E extends Exception> Collection<T> isSingle(Collection<T> value, Supplier<E> exceptionSupplier)
            throws E {
        if (value != null && Routines.getSize(value) == 1) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    public static <T, E extends Exception> T[] isSingleOrNull(T[] value, Supplier<E> exceptionSupplier) throws E {
        if (value != null && value.length <= 1) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    public static <T extends Collection, E extends Exception> T isSingleOrNull(T value, Supplier<E> exceptionSupplier)
            throws E {
        if (value != null && value.size() <= 1) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    @NotNull
    public static <T, E extends Exception> T isInstance(Object value, Class<T> clazz) throws E {
        return isInstance(value, clazz, () -> new AssertionException("Несовместимый класс"));
    }

    public static boolean instanceOf(Class clazz, Class... list) {
        if (list == null) {
            return false;
        }
        for (Class item : list) {
            if (!item.isAssignableFrom(clazz)) {
                return false;
            }
        }
        return true;
    }

    public static boolean instanceOfAny(Class clazz, Class... list) {
        if (list == null) {
            return false;
        }
        for (Class item : list) {
            if (item.isAssignableFrom(clazz)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInstanceOf(Object value, Class clazz) {
        return value != null ? clazz.isAssignableFrom(value.getClass()) : false;
    }

    @NotNull
    public static <T, E extends Exception> T isInstance(Object value, Class<T> clazz, Supplier<E> exceptionSupplier)
            throws E {
        if (Routines.isInstanceOf(value, clazz)) {
            return (T) value;
        }
        throw exceptionSupplier.get();
    }

    public static <T, E extends Exception> T isInstanceOrNull(Object value, Class<T> clazz, Supplier<E> exceptionSupplier)
            throws E {
        if (value == null) {
            return null;
        }
        if (Routines.isInstanceOf(value, clazz)) {
            return (T) value;
        }
        throw exceptionSupplier.get();
    }

    @NotNull
    public static <T, E extends Exception> String isNotEmpty(String value, Supplier<E> exceptionSupplier) throws E {
        if (value != null && Routines.getSize(value) > 0) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    @NotNull
    public static <T, E extends Exception> T[] isNotEmpty(T[] value, Supplier<E> exceptionSupplier) throws E {
        if (value != null && Routines.getSize(value) > 0) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    @NotNull
    public static <T extends Collection, E extends Exception> T isNotEmpty(T value, Supplier<E> exceptionSupplier)
            throws E {
        if (value != null && Routines.getSize(value) > 0) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    public static <T extends Collection, E extends Exception> T isEmpty(T value, Supplier<E> exceptionSupplier) throws E {
        if (value == null || Routines.getSize(value) == 0) {
            return value;
        }
        throw exceptionSupplier.get();
    }

    @NotNull
    public static <T, E extends Exception> T[] isSizeBetween(T[] value, int min, int max, Supplier<E> exceptionSupplier)
            throws E {
        if (value == null || !Routines.isBetween(Routines.getSize(value), min, max)) {
            throw exceptionSupplier.get();
        }
        return value;
    }

    @NotNull
    public static <T, E extends Exception> Collection<T> isSizeBetween(Collection<? extends T> value, int min, int max,
                                                                       Supplier<E> exceptionSupplier) throws E {
        if (value == null || !Routines.isBetween(Routines.getSize(value), min, max)) {
            throw exceptionSupplier.get();
        }
        return (Collection<T>) value;
    }

    @NotNull
    public static <E extends Exception> boolean isTrue(Boolean value, Supplier<E> exceptionSupplier) throws E {
        if (value == null || value != true) {
            throw exceptionSupplier.get();
        }
        return value;
    }

    @NotNull
    public static <E extends Exception> boolean isFalse(Boolean value, Supplier<E> exceptionSupplier) throws E {
        if (value == null || value != false) {
            throw exceptionSupplier.get();
        }
        return value;
    }

    public static <T, E extends Exception> T isEqual(T value1, T value2, Supplier<E> exceptionSupplier) throws E {
        if (!Routines.isEqual(value1, value2)) {
            throw exceptionSupplier.get();
        }
        return value1;
    }

    public static class AssertionException extends RuntimeException {

        public AssertionException(String message) {
            super(message);
        }

        public AssertionException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
