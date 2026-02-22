package com.codigoagil.demo.dtos;

// Usamos 'record' de Java en lugar de 'class'. Es inmutable y genera los getters automáticamente.
public record UsuarioResponseDTO(
        Long id,
        String nombre,
        String email,
        String rol,
        Boolean activo
) {}