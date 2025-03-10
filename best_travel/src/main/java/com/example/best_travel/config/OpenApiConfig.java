package com.example.best_travel.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Best Travel API",
        version = "1.0",
        description = "Documentation Best Travel API v1.0")
)
public class OpenApiConfig {

}
