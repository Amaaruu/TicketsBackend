package com.codigoagil.demo.dtos;

import java.time.LocalDateTime;

public record TicketResponseDTO(
        Long id,
        String titulo,
        String descripcion,
        String clienteNombre,
        String agenteNombre,
        String categoria,
        String estado,
        String prioridad,
        LocalDateTime creadoEn,
        LocalDateTime actualizadoEn
) {}