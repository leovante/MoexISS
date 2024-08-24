package ru.exdata.moex.utils;

import jakarta.validation.constraints.NotNull;

import java.util.Collection;

public class DefaultAssertions extends Assertions {

    @NotNull
    public static <T, E extends Exception> T isNotNull(T value) throws E {
        return isNotNull(value, () -> new NullValueAssertionException("Значение не должно быть NULL"));
    }

    public static <T, E extends Exception> T[] isSingle(T[] value) throws E {
        return isSingle(value, () -> new AssertionException("Ожидается одиночное значение"));
    }

    public static <T, E extends Exception> Collection<T> isSingle(Collection<T> value) throws E {
        return isSingle(value, () -> new AssertionException("Ожидается одиночное значение"));
    }


    public static <T, E extends Exception> T[] isSingleOrNull(T[] value) throws E {
        return isSingleOrNull(value, () -> new AssertionException("Ожидается одиночное или пустое значение"));
    }

    public static <T extends Collection, E extends Exception> T isSingleOrNull(T value) throws E {
        return isSingleOrNull(value, () -> new AssertionException("Ожидается одиночное или пустое значение"));
    }

    public static <T, E extends Exception> T isInstance(Object value, Class<T> clazz) throws E {
        return isInstance(value, clazz, () -> new ClassCompatibilityAssertionException("Несовместимый класс"));
    }

    public static <T, E extends Exception> T isInstanceOrNull(Object value, Class<T> clazz) throws E {
        return isInstanceOrNull(value, clazz, () -> new ClassCompatibilityAssertionException("Несовместимый класс"));
    }

    @NotNull
    public static <E extends Exception> boolean isTrue(Boolean value) throws E {
        return isTrue(value, () -> new AssertionException("Ожидается значение TRUE"));
    }

    @NotNull
    public static <E extends Exception> boolean isFalse(Boolean value) throws E {
        return isFalse(value, () -> new AssertionException("Ожидается значение FALSE"));
    }

    public static <T, E extends Exception> T isEqual(T value1, T value2) throws E {
        return isEqual(value1, value2, () -> new AssertionException("Значение отличается от ожидаемого"));
    }

    @NotNull
    public static <T extends Collection, E extends Exception> T isNotEmpty(T value) throws E {
        return isNotEmpty(value, () -> new RangeAssertionException("Пустой список значений"));
    }

    public static class MissingValueAssertionException extends AssertionException {

        public MissingValueAssertionException() {
            this("Отсутствует значение");
        }

        public MissingValueAssertionException(String message) {
            super(message);
        }
    }

    public static class NullValueAssertionException extends MissingValueAssertionException {

        public NullValueAssertionException() {
            this("Недопустимое значение null");
        }

        public NullValueAssertionException(String message) {
            super(message);
        }
    }

    public static class EmptyValueAssertionException extends MissingValueAssertionException {

        public EmptyValueAssertionException() {
            this("Пустое значение");
        }

        public EmptyValueAssertionException(String message) {
            super(message);
        }
    }

    public static class RangeAssertionException extends AssertionException {

        public RangeAssertionException() {
            this("Некорректный диапазон значений");
        }

        public RangeAssertionException(String message) {
            super(message);
        }
    }

    public static class IntervalRangeAssertionException extends RangeAssertionException {

        public IntervalRangeAssertionException() {
            this("Некорректный интервал");
        }

        public IntervalRangeAssertionException(String message) {
            super(message);
        }
    }

    public static class ClassCompatibilityAssertionException extends AssertionException {

        public ClassCompatibilityAssertionException() {
            this("Несовместимые классы");
        }

        public ClassCompatibilityAssertionException(String message) {
            super(message);
        }
    }

}
