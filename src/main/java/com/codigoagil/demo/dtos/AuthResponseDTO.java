package com.codigoagil.demo.dtos;

public record AuthResponseDTO(
        String token,
        Long id,          
        String email,
        String rol
) {}