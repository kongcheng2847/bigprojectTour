package com.hqj.bigproject.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilJson {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object bean) {
        return toJson(bean, true);
    }

    public static String toJson(Object bean, boolean includeNull) {
        try {
            if (bean instanceof String) return ((String) bean);
            String json;
            if (includeNull) {
                json = mapper.writeValueAsString(bean);
            }
            else {
                ObjectMapper mapper = new ObjectMapper();
                //mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
                mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                json = mapper.writeValueAsString(bean);
            }

            return json;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <E> E parseJson(String json, Class clazz) {
        try {
            Object bean = mapper.readValue(json, clazz);
            return (E) bean;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
