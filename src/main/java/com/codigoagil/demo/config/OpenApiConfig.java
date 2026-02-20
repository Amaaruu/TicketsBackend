package com.codigoagil.demo.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "API de Sistema de Tickets (Helpdesk)",
        version = "1.0.0",
        description = "Documentación interactiva de la API REST para el portafolio de Analista Programador.",
        contact = @Contact(
            name = "Isaac",
            email = "igonzalez@codigoagil.cl"
        )
    )
)
public class OpenApiConfig {
}