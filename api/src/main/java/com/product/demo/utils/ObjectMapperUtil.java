package com.product.demo.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.product.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ObjectMapperUtil {

    //TODO make this a standard object mapper

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        objectMapper.registerModule(new Jdk8Module());
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT)));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.registerModule(new GuavaModule());
        objectMapper.registerModule(new JodaModule());

        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE));
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static String toJson(Object object) throws ApiException {
        try {
            if (object != null)
                return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> valueType) throws ApiException {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    public static <T> List<T> fromJsonToList(String json, Class<T> valueType) throws ApiException {
        try {
            return objectMapper.readValue(json, new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    public static <T> T fromJson(byte[] json, Class<T> valueType) throws ApiException {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeRef) {
        try {
            return objectMapper.readValue(json, typeRef);
        } catch (IOException e) {
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }

    public static <T> T clone(Object object, Class<T> clazz) {
        if (object == null) {
            return null;
        }
        try {
            String json = ObjectMapperUtil.toJson(object);
            return objectMapper.readValue(json, clazz);

        } catch (IOException e) {
            throw new ApiException(Response.Status.INTERNAL_SERVER_ERROR, e);
        }
    }
}
