package com.springboot.javarestapi.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.javarestapi.exception.InternalServerErrorException;

import java.util.LinkedHashMap;
import java.util.List;

public class JsonBuildObjectConverter<T> {

    public T ConvertSingleObject(LinkedHashMap<Object, Object> singlesObject) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(singlesObject);
            T result = objectMapper.readValue(jsonString, new TypeReference<>() {
            });

            return result;
        } catch (JsonProcessingException e) {
            // handle exception
            throw new InternalServerErrorException("Error get detail on converter");
        }
    }

    public List<T> ConvertListObject(List<LinkedHashMap<Object, Object>> list) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(list);
            List<T> result = objectMapper.readValue(jsonString, new TypeReference<>() {
            });

            return result;
        } catch (JsonProcessingException e) {
            // handle exception
            throw new InternalServerErrorException("Error get list on converter");
        }
    }
}
