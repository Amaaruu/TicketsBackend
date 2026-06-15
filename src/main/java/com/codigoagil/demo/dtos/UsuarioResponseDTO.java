package com.codigoagil.demo.dtos;

public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        String rol,
        Boolean activo
) {}