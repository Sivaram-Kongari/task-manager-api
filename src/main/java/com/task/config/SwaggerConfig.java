package com.task.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI customOpenAPI() {

		return new OpenAPI()
				.info(new Info()
						.title("TaskMate API")
						.version("1.0")
						.description("Task management API with JWT-based authentication"))
				.components(new Components()
						.addSecuritySchemes("bearerAuth",
								new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
								)
						)
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
	}
}
