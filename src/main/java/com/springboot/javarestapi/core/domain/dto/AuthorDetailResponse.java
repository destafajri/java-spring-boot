package com.springboot.javarestapi.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.sql.Timestamp;
import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AuthorDetailResponse {

    private UUID id;
    private UUID userId;
    private String username;
    private String email;
    private String name;
    private String role;
    @JsonProperty("is_active")
    private boolean isActive;
    private String createdAt;
    private String updatedAt;
}
