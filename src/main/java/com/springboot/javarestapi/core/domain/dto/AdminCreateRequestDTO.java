package com.springboot.javarestapi.core.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AdminCreateRequestDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
