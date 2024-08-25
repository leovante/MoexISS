package ru.exdata.moex.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Arrays;

import static com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping.NON_FINAL;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class ConvertRoutines {

    public static final ObjectMapper OBJECT_MAPPER = new DefaultObjectMapper();

    public static final ObjectMapper FAILSAFE_OBJECT_MAPPER = new FailsafeObjectMapper();

    public static final ObjectMapper TRANSPORT_OBJECT_MAPPER = new TransportObjectMapper();

    public static <T> T convertOverJson(Object value, Class<T> clazz) {
        return convertOverJson(OBJECT_MAPPER, value, clazz);
    }

    public static <T> T convertOverJson(ObjectMapper objectMapper, Object value, Class<T> clazz) {
        return value != null ? fromJson(objectMapper, toJson(value), clazz) : null;
    }

    @SneakyThrows
    public static String toJson(ObjectMapper objectMapper, Object value) {
        return value != null ? objectMapper.writeValueAsString(value) : null;
    }

    public static String toJson(Object value) {
        return toJson(OBJECT_MAPPER, value);
    }

    @SneakyThrows
    public static <T> T fromJson(String value, Class<T> clazz) {
        return fromJson(OBJECT_MAPPER, value, clazz);
    }

    @SneakyThrows
    public static <T> T fromJson(ObjectMapper objectMapper, String value, Class<T> clazz) {
        return value != null ? objectMapper.readValue(value, clazz) : null;
    }

    public static Long toLong(Object value) {
        try {
            return value != null ? Long.valueOf(Routines.toString(value)) : null;
        } catch (RuntimeException exception) {
            throw new ConvertException("Ошибка преобразования значения в Long", exception);
        }
    }

    public static Integer toInteger(Object value) {
        try {
            return value != null ? Integer.valueOf(Routines.toString(value)) : null;
        } catch (RuntimeException exception) {
            throw new ConvertException("Ошибка преобразования значения в Integer", exception);
        }
    }

    public static Byte toByte(Object value) {
        try {
            return value != null ? Byte.valueOf(Routines.toString(value)) : null;
        } catch (RuntimeException exception) {
            throw new ConvertException("Ошибка преобразования значения в Integer", exception);
        }
    }

    public static <T> T fromTransportJson(String value, Class<T> clazz) {
        return fromJson(TRANSPORT_OBJECT_MAPPER, value, clazz);
    }

    public static String toTransportJson(Object value) {
        return toJson(TRANSPORT_OBJECT_MAPPER, value);
    }

    public static Boolean toBoolean(Object value) {
        try {
            return value != null ? Boolean.valueOf(Routines.toString(value)) : null;
        } catch (RuntimeException exception) {
            throw new ConvertException("Ошибка преобразования значения в Boolean", exception);
        }
    }

    public static class ConvertException extends RuntimeException {

        public ConvertException() {
        }

        public ConvertException(String message) {
            super(message);
        }

        public ConvertException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class DefaultObjectMapper extends ObjectMapper {

        public DefaultObjectMapper() {
            disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            disable(WRITE_DATES_AS_TIMESTAMPS);
            registerModule(new JavaTimeModule());
            registerModule(new Jdk8Module());
        }
    }

    public static class FailsafeObjectMapper extends ObjectMapper {

        public FailsafeObjectMapper() {
            disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            disable(WRITE_DATES_AS_TIMESTAMPS);
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            registerModule(new JavaTimeModule());
            registerModule(new Jdk8Module());
        }
    }

    public static class TransportObjectMapper extends ObjectMapper {

        public TransportObjectMapper() {
            PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                    .allowIfBaseType(Object.class)
                    .build();
            activateDefaultTyping(ptv); // default to using DefaultTyping.OBJECT_AND_NON_CONCRETE
            activateDefaultTyping(ptv, NON_FINAL);

            disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
            configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

            disable(WRITE_DATES_AS_TIMESTAMPS); //objectMapper.findAndRegisterModules();
            registerModule(new Jdk8Module());
            registerModule(new JavaTimeModule());
            SimpleModule keySerializerModule = new SimpleModule("CustomKeySerializerModule", new Version(1, 0, 0, null));
            keySerializerModule.addKeySerializer(Object.class, new JsonSerializer() {
                @Override
                public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    if (value != null && value.getClass().isEnum()) {
                        gen.writeFieldName(OBJECT_MAPPER.writeValueAsString(
                                Arrays.asList(value.getClass(), value)));
                    } else {
                        gen.writeFieldName(TRANSPORT_OBJECT_MAPPER.writeValueAsString(value));
                    }
                }
            });
            keySerializerModule.addKeyDeserializer(Object.class, new KeyDeserializer() {
                @Override
                public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
                    return TRANSPORT_OBJECT_MAPPER.readValue(key, Object.class);
                }
            });
            registerModule(keySerializerModule);
        }
    }
}
