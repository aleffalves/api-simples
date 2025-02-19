package com.aleffalves.api_simples.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterDtoUtils {

    public static Map<String, Object> filterFields(Object dto, List<String> fields) {
        Map<String, Object> filteredDto = new HashMap<>();

        for (Field field : dto.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (fields == null || fields.isEmpty() || fields.contains(field.getName())) {
                try {
                    filteredDto.put(field.getName(), field.get(dto));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return filteredDto;
    }

}
