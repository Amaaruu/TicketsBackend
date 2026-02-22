package com.codigoagil.demo.dtos;

public record AuthResponseDTO(
        String token,
        String email,
        String rol
) {}