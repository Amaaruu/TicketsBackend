package com.codigoagil.demo.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Sistema de Tickets (Helpdesk)",
        version = "1.0.0",
        description = "Documentación interactiva de la API REST para el portafolio de Analista Programador.",
        contact = @Contact(
            name = "Isaac",
            email = "tu.correo@ejemplo.com"
        )
    ),
    security = @SecurityRequirement(name = "Bearer Authentication") // Le dice a Swagger que todos los endpoints requieren seguridad
)
@SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
public class OpenApiConfig {
}