package com.springboot.javarestapi.metadata;

import org.springframework.data.domain.Sort;

public class PagintationUtility {

    public static Sort.Direction getOrderBy(String sortBy) {
        if (sortBy.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else {
            return Sort.Direction.DESC;
        }
    }
}
