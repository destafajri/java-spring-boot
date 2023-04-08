package com.springboot.javarestapi.core.domain.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.*;
import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class AdminCreateRequestDTO {

    @NotBlank
    private String name;
    @NotBlank
    @Email(message = "must be email format")
    private String email;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$", message = "must be username format")
    private String username;
    @NotBlank
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters long")
    private String password;
}
