package com.springboot.javarestapi.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.springboot.javarestapi.metadata.Metadata;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ResponseData<T> {

    private Integer code;
    private HttpStatus status;
    private List<String> message;
    private T data;

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Data
    public static class WithMeta<T> {
        private Integer code;
        private HttpStatus status;
        private List<String> message;
        private Metadata meta;
        private T data;
    }
}
