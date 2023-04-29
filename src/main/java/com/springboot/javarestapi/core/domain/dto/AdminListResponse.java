package com.springboot.javarestapi.core.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.UUID;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AdminListResponse {

    private UUID id;
    private UUID userId;
    private String username;
    private String name;
    @JsonProperty("is_active")
    private boolean isActive;
    private String createdAt;
    private String updatedAt;
}
