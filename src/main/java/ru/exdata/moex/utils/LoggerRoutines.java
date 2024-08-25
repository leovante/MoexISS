package ru.exdata.moex.utils;


import ru.exdata.moex.utils.interfaces.SupplierMethodInvocation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class LoggerRoutines {

    private LoggerRoutines() {
    }

    public static String toStringAsStackTrace(Throwable value) {
        if (value == null) {
            return null;
        }
        try (ByteArrayOutputStream buf = new ByteArrayOutputStream()) {
            try (PrintWriter pw = new PrintWriter(buf)) {
                value.printStackTrace(pw);
                pw.flush();
            }
            buf.flush();
            return buf.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object toStringAsJson(final Object value) {
        return new Object() {
            @Override
            public String toString() {
                return value != null ? ConvertRoutines.toJson(value) : "";
            }
        };
    }

    public static Object toStringAsFormattedJson(final Object value) {
        return new Object() {
            @Override
            public String toString() {
                return value != null ? ConvertRoutines.toJson(value) : "";
            }
        };
    }

    public static Object toStringAsJsonWithClass(final Object value) {
        return new Object() {
            @Override
            public String toString() {
                return value != null ? String.format("%s %s", value.getClass().getSimpleName(), ConvertRoutines.toJson(value))
                        : "";
            }
        };
    }

    public static Object toFormattedString(final String pattern, final Object... arguments) {
        return new Object() {
            @Override
            public String toString() {
                return pattern != null ? String.format(pattern, arguments) : null;
            }
        };
    }

    public static Object toString(SupplierMethodInvocation<String, Exception> value) {
        return new Object() {
            @Override
            public String toString() {
                try {
                    return value != null ? value.invoke() : null;
                } catch (Exception e) {
                    return String.format("Ошибка формирования строкового значения: %s", e.getMessage());
                }
            }
        };
    }

}
