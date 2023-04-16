package com.springboot.javarestapi.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.javarestapi.core.domain.dto.AuthorListResponse;
import com.springboot.javarestapi.exception.InternalServerErrorException;
import com.springboot.javarestapi.metadata.Pagintation;

import java.util.LinkedHashMap;
import java.util.List;

public class JsonBuildObjectConverter<T> {

    public List<T> ConvertToJavaObject(List<LinkedHashMap<String, Object>> list){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(list);
            List<T> result = objectMapper.readValue(jsonString, new TypeReference<>() {
            });

            return result;
        } catch (JsonProcessingException e) {
            // handle exception
            throw new InternalServerErrorException("Error get list author on database");
        }
    }
}