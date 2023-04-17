package com.springboot.javarestapi.core.domain.dto;

import com.springboot.javarestapi.metadata.Metadata;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class ResponseUtility {
    public static <T> ResponseData.WithMeta<T> createResultWithMetaDTO(Integer pages, Integer perPage, Integer total, String sortBy, String order, String message, T data) {
        Metadata meta = new Metadata();
        meta.setPage(pages);
        meta.setPerPage(perPage);
        meta.setTotal(total);
        meta.setSortBy(sortBy);
        meta.setOrderBy(order);

        ResponseData.WithMeta<T> result = new ResponseData.WithMeta<>();
        result.setCode(HttpStatus.OK.value());
        result.setStatus(HttpStatus.OK);
        result.setMessage(Collections.singletonList(message));
        result.setMeta(meta);
        result.setData(data);

        return result;
    }

    public static <T> ResponseData<T> createResultDTO(String message, T data) {
        ResponseData<T> result = new ResponseData<>();
        result.setCode(HttpStatus.OK.value());
        result.setStatus(HttpStatus.OK);
        result.setMessage(Collections.singletonList(message));
        result.setData(data);

        return result;
    }
}
