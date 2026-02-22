package com.codigoagil.demo.dtos;

import java.time.LocalDateTime;

public record ComentarioResponseDTO(
        Long id,
        String autorNombre,
        String autorRol,
        String mensaje,
        LocalDateTime creadoEn
) {}