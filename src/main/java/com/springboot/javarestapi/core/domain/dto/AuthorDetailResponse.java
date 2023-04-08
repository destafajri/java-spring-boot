package com.springboot.javarestapi.core.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AuthorDetailResponse {

    private UUID id;
    private UUID userId;
    private String username;
    private String name;
    private String role;
    private boolean isActive;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
