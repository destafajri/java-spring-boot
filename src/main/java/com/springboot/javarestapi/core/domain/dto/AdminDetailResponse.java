package com.springboot.javarestapi.core.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AdminDetailResponse {

    private UUID id;
    private UUID userId;
    private String username;
    private String name;
    private String role;
    private boolean is_active;
    private Timestamp created_at;
    private Timestamp updated_at;
}
