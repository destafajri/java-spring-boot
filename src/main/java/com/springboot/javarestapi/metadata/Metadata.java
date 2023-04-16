package com.springboot.javarestapi.metadata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Metadata {

    private Integer page;
    private Integer perPage;
    private Integer total;
    private String sortBy;
    private String orderBy;

    @JsonIgnore
    public Integer getPageForQuery() {
        this.page = page;
        Integer pages = page;
        if (page <= 0) {
            page = 1;
            return pages = 0;
        } else if (page > 0) {
            return pages = pages - 1;
        }

        return pages;
    }

}
