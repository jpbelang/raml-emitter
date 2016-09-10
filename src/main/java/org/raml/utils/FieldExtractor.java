package org.raml.utils;

import java.lang.reflect.Field;

public class FieldExtractor {

    public static <T> T getFieldValue(String fieldName, Object instance)
        throws IllegalAccessException, NoSuchFieldException {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (T) field.get(instance);
    }
}
