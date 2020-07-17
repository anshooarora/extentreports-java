package com.aventstack.extentreports.markuputils.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionUtil {
    public static List<Field> getFieldsIgnoringAnnotation(Class<?> clazz,
            Class<? extends Annotation> skippingAnnotationClazz) {
        if (clazz == null)
            return Collections.emptyList();
        List<Field> fields = new ArrayList<>(
                getFieldsIgnoringAnnotation(clazz.getSuperclass(), skippingAnnotationClazz));
        List<Field> filteredList = Arrays.stream(clazz.getDeclaredFields())
                .filter(x -> !x.isAnnotationPresent(skippingAnnotationClazz))
                .collect(Collectors.toList());
        fields.addAll(filteredList);
        return fields;
    }

    public static List<Field> getAnnotatedFields(Class<?> clazz,
            Class<? extends Annotation> annotationClazz) {
        if (clazz == null)
            return Collections.emptyList();
        List<Field> fields = new ArrayList<>(getAnnotatedFields(clazz.getSuperclass(), annotationClazz));
        List<Field> filteredList = Arrays.stream(clazz.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(annotationClazz))
                .collect(Collectors.toList());
        fields.addAll(filteredList);
        return fields;
    }
}
