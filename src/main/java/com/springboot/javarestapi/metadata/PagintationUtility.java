package com.springboot.javarestapi.metadata;

import com.springboot.javarestapi.core.domain.dto.ResponseData;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.util.Collections;

public class PagintationUtility {

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

    public static Sort.Direction getOrderBy(String sortBy) {
        if (sortBy.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }
}
