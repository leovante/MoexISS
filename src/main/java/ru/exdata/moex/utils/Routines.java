package ru.exdata.moex.utils;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.io.ByteStreams;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import ru.exdata.moex.utils.interfaces.HasCode;

import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Routines {

    public static <T> boolean isEqualsRelatives(T v1, T v2) {
        return isEqual(v1, v2);
    }

    public static boolean isEqualsStrings(Object v1, Object v2) {
        return isEqual(toString(v1), toString(v2));
    }

    public static <T> boolean isEqual(T v1, T v2) {
        return ((v1 == null && v2 == null) || (v1 != null && v1.equals(v2)));
    }

    public static <T extends Enum> boolean isEqual(T v1, T v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(Integer v1, Integer v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(Long v1, Long v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(Timestamp v1, Timestamp v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(Date v1, Date v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(Calendar v1, Calendar v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(GregorianCalendar v1, GregorianCalendar v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(Float v1, Float v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(Double v1, Double v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(String v1, String v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(Byte v1, Byte v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(Character v1, Character v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqual(BigDecimal v1, BigDecimal v2) {
        return isEqual((Object) v1, (Object) v2);
    }

    public static boolean isEqualsIgnoreCase(String v1, String v2) {
        return ((v1 == null && v2 == null) || (v1 != null && v1.equalsIgnoreCase(v2)));
    }

    public static boolean isEqualsIgnoreCase(Object v1, Object v2) {
        return isEqualsIgnoreCase(toString(v1), toString(v2));
    }

    public static boolean isEqual(String value, Enum enumValue) {
        return isEqual(value, enumValue != null ? enumValue.name() : null);
    }

    public static boolean isNotEqual(Double value1, Double value2) {
        return !isEqual(value1, value2);
    }

    public static boolean isNotEqual(String value1, String value2) {
        return !isEqual(value1, value2);
    }

    public static boolean isNotEqual(Object value1, Object value2) {
        return !isEqual(value1, value2);
    }

    public static <T> T getCode(HasCode<T> value) {
        return value != null ? value.getCode() : null;
    }

    public static String getName(Enum value) {
        return value != null ? value.name() : null;
    }

    public static String toString(Object value) {
        return value != null ? value.toString() : null;
    }

    public static <T extends OutputStream> T readStream(InputStream in, T out) throws IOException {
        ByteStreams.copy(in, out);
        out.flush();
        return out;
    }

    public static boolean isEmpty(Object value) {
        return value == null || value.toString().trim().isEmpty();
    }

    public static boolean isEmpty(Collection value) {
        return value == null || value.isEmpty();
    }

    public static <T> Collection<T> nullSaveCollection(Collection<T> collection) {
        return collection == null ? new ArrayList<T>() : collection;
    }

    public static boolean isEmpty(Object[] value) {
        return value == null || value.length == 0;
    }

    public static boolean isEmpty(Map value) {
        return value == null || value.isEmpty();
    }

    public static boolean isEmpty(Iterator value) {
        return value == null || !value.hasNext();
    }

    public static int getSize(Collection value) {
        return value != null ? value.size() : 0;
    }

    public static int getSize(String value) {
        return value != null ? value.length() : 0;
    }

    public static int getSize(Object[] value) {
        return value != null ? value.length : 0;
    }

    public static int getSize(long[] value) {
        return value != null ? value.length : 0;
    }

    public static int getSize(byte[] value) {
        return value != null ? value.length : 0;
    }

    public static int getSize(int[] value) {
        return value != null ? value.length : 0;
    }

    public static int getSize(char[] value) {
        return value != null ? value.length : 0;
    }

    public static int getSize(boolean[] value) {
        return value != null ? value.length : 0;
    }

    public static int getSize(float[] value) {
        return value != null ? value.length : 0;
    }

    public static int getSize(double[] value) {
        return value != null ? value.length : 0;
    }

    public static boolean isBetween(long value, long min, long max) {
        return value >= Math.min(min, max) && value <= Math.max(min, max);
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

    public static <T, R extends HasCode<T>> R fromCode(R[] values, T code) {
        if (values == null) {
            return null;
        }
        for (R item : values) {
            if (isEqual(item.getCode(), code)) {
                return item;
            }
        }
        return null;
    }

    public static <T, R extends HasCode<T>> R fromCode(Class<Enum<? extends HasCode<T>>> clazz, T code) {
        if (clazz == null) {
            return null;
        }
        Enum<? extends HasCode<T>>[] enumConstants = clazz.getEnumConstants();
        for (Enum<? extends HasCode<T>> item : enumConstants) {
            if (isEqual(((HasCode) item).getCode(), code)) {
                return (R) item;
            }
        }
        return null;
    }

    public static <T extends Enum> T fromName(T[] values, String name) {
        if (values == null) {
            return null;
        }
        for (T item : values) {
            if (isEqual(item.name(), name)) {
                return item;
            }
        }
        return null;
    }

    public static <T extends Enum> T fromName(Class<T> clazz, String name) {
        return clazz != null && name != null ? (T) Enum.valueOf(clazz, name) : null;
    }

    public static <T> T getFirst(T[] object) {
        return (!isEmpty(object)) ? object[0] : null;
    }

    public static <T> T getFirst(Collection<? extends T> object) {
        return (!isEmpty(object)) ? object.iterator().next() : null;
    }

    public static <T> T getLast(T[] object) {
        return (!isEmpty(object)) ? object[object.length - 1] : null;
    }

    public static <T> T getLast(Collection<T> object) {
        return !isEmpty(object) ? getLast((T[]) object.toArray()) : null;
    }

    /*public static <T> T getSingle(T[] object) {
        return getFirst(DefaultAssertions.isSingle(object));
    }

    public static <T> T getSingle(Collection<? extends T> object) {
        return getFirst(DefaultAssertions.isSingle(object));
    }

    public static <T> T getSingleOrNull(T[] object) {
        return object != null ? getFirst(Assertions.isSizeBetween(object, 0, 1,
                () -> new DefaultAssertions.IntervalRangeAssertionException("Допустимо не более 1 значения"))) : null;
    }

    public static <T> T getSingleOrNull(Collection<? extends T> object) {
        return object != null ? getFirst(
                Assertions.<T, DefaultAssertions.IntervalRangeAssertionException>isSizeBetween(object, 0, 1,
                        () -> new DefaultAssertions.IntervalRangeAssertionException("Допустимо не более 1 значения"))) : null;
    }

    public static <T> T getSingleNotNull(T... values) {
        return getSingle(
                Arrays.asList(DefaultAssertions.isNotNull(values)).stream().filter(item -> item != null)
                        .collect(Collectors.toList()));
    }*/

    public static <V> boolean contains(V value, V... list) {
        if (list == null) {
            return false;
        }
        for (V item : list) {
            if (isEqual(value, item)) {
                return true;
            }
        }
        return false;
    }

    public static <V> boolean contains(V value, Collection list) {
        if (list == null) {
            return false;
        }
        for (Object item : list) {
            if (isEqual(value, item)) {
                return true;
            }
        }
        return false;
    }

    public static <V> boolean contains(V[] iterable, V... list) {
        if (Routines.isEmpty(list)) {
            return false;
        }
        Set<V> set = new HashSet<V>();
        set.addAll(Arrays.asList(list));
        for (V item : iterable) {
            if (!set.contains(item)) {
                return false;
            }
        }
        return true;
    }

    public static <V> boolean containsAny(V[] iterable, V... list) {
        if (list == null) {
            return false;
        }
        Set<V> set = new HashSet<V>();
        set.addAll(Arrays.asList(list));
        for (V item : iterable) {
            if (set.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public static <V> boolean containsAny(Collection<V> iterable, V... list) {
        if (iterable == null || list == null) {
            return false;
        }
        Set<V> set = new HashSet<V>();
        set.addAll(Arrays.asList(list));
        for (V item : iterable) {
            if (set.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public static <K, V> boolean contains(Map<K, V> map, K key) {
        if (map == null) {
            return false;
        }
        return map.containsKey(key);
    }

    public static <T extends Serializable> T clone(T object) {
        if (object == null) {
            return null;
        }
        try (ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
            try (ObjectOutputStream out = new ObjectOutputStream(buf)) {
                out.writeObject(object);
                out.flush();
            }

            try (ByteArrayInputStream bis = new ByteArrayInputStream(buf.toByteArray())) {
                try (ObjectInputStream is = new ObjectInputStream(bis)) {
                    return (T) is.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to clone object: " + object, e);
        }
    }

    public static <T> T ifNull(T value, Supplier<? extends T> def) {
        return value != null ? value : def.get();
    }

    public static <T> T ifNull(T value, T def) {
        return value != null ? value : def;
    }

    public static <R, T> R ifNotNull(T value, Function<T, R> supplier) {
        return value != null ? supplier.apply(value) : null;
    }

    public static <R, T> R ifNotEmpty(Collection<T> value, Function<Collection<T>, R> supplier) {
        return !isEmpty(value) ? supplier.apply(value) : null;
    }

    @NotNull
    public static boolean isTrue(Boolean value) {
        return value != null && value;
    }

    @NotNull
    public static boolean isFalse(Boolean value) {
        return value != null && !value;
    }

    public static Boolean not(Boolean value) {
        return value != null ? !value : null;
    }

    public static <T> T getFirstNotNull(T... values) {
        if (values == null) {
            return null;
        }
        for (T item : values) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    public static <T> T getFirstNotNull(Collection<T> values) {
        if (values == null) {
            return null;
        }
        for (T item : values) {
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    public static <T> T getFirstNotNull(Supplier<T>... values) {
        if (values == null) {
            return null;
        }
        for (Supplier<T> item : values) {
            T value = item.get();
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    public static <T> List<T> emptyList() {
        return new ArrayList<>();
    }

    public static void sleep(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static Set<Field> getDeclaredFields(Class clazz) {
        Set<Field> result = new HashSet<Field>();
        while (clazz != null && clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                result.add(field);
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    public static <T extends Exception> T findException(Throwable e, Predicate<Throwable> filter) {
        if (e != null) {
            Set<Throwable> chain = new LinkedHashSet();
            do {
                if (filter.apply(e)) {
                    return (T) e;
                }
                chain.add(e);
                e = e.getCause();
            } while (e != null && !chain.contains(e));
        }
        return null;
    }

    /*public static <T> T proxyUnwrap(T value) {
        if (value == null) {
            return null;
        }
        if (AopUtils.isAopProxy(value) && value instanceof Advised) {
            T target = null;
            try {
                target = (T) ((Advised) value).getTargetSource().getTarget();
            } catch (Exception e) {
                throw new RuntimeException("Ошибка получения целевого объекта");
            }
            return target;
        }
        return value;
    }*/

    public static String joinNotEmpty(String glue, Collection values) {
        if (values == null) {
            return null;
        }
        List data = (List) values.stream().filter(item -> !Routines.isEmpty(item)).collect(Collectors.toList());
        return Joiner.on(glue).join(data);
    }

    public static String join(String glue, Collection values) {
        return Joiner.on(glue).join(values);
    }

    public static String joinNotEmpty(String glue, Object... values) {
        return joinNotEmpty(glue, Arrays.asList(values));
    }

    public static String join(String glue, Object... values) {
        return join(glue, Arrays.asList(values));
    }

    public static String toUpperCase(String value) {
        return value != null ? value.toUpperCase() : null;
    }

    public static String toLowerCase(String value) {
        return value != null ? value.toLowerCase() : null;
    }

    public static String decapitalize(String value) {
        return !isEmpty(value) ? (String.format("%s%s", toLowerCase(value.substring(0, 1)), value.substring(1))) : value;
    }

    public static String capitalize(String value) {
        return !isEmpty(value) ? (String.format("%s%s", toUpperCase(value.substring(0, 1)), value.substring(1))) : value;
    }

    public static String repeat(String value, Integer count) {
        return (value != null && count != null) ? StringUtils.repeat(value, count) : null;
    }

    public static Class getClassFromNameOrNullIfMissing(String value) {
        try {
            return value != null ? Class.forName(value) : null;
        } catch (Throwable e) {
            return null;
        }
    }

    public static <T> Optional<T> optionalOf(T value) {
        return value != null ? Optional.of(value) : Optional.empty();
    }

    public static <T> T valueOf(Optional<T> value) {
        return ifNotNull(value, v -> v.orElse(null));
    }

    public static <T> Set<T> setOf(T... value) {
        return value != null ? Set.of(value) : emptySet();
    }

    public static <T> Set<T> emptySet() {
        return new HashSet<>();
    }

    public static <T> Stream<T> streamOf(T... value) {
        return value != null ? Stream.of(value) : Stream.empty();
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends U> valueMapper) {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    Map<K, U> map = new LinkedHashMap<>();
                    list.forEach(item -> {
                        K key = keyMapper.apply(item);
                        if (map.containsKey(key)) {
                            throw new IllegalStateException(String.format("Duplicate key %s", key));
                        }
                        map.put(key, valueMapper.apply(item));
                    });
                    return map;
                }
        );
    }

    public static <T> boolean equalsUnsorted(Collection<T> value1, Collection<T> value2) {
        if (value1 == null && value2 == null) return true;
        if (value1 == null || value2 == null) return false;
        if (!(Routines.<Integer>isEqual(getSize(value1), getSize(value2)))) return false;
        if (!value1.containsAll(value2)) return false;
        if (!value2.containsAll(value1)) return false;
        return true;
    }
}
