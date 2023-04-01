package com.springboot.javarestapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "credential")
@Data
public class CredentialProperties {

    private String APIKey;
}
