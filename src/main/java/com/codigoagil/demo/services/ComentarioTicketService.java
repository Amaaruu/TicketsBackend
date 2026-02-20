package com.codigoagil.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codigoagil.demo.models.ComentarioTicket;
import com.codigoagil.demo.models.Ticket;
import com.codigoagil.demo.repositories.ComentarioTicketRepository;
import com.codigoagil.demo.repositories.TicketRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComentarioTicketService {

    private final ComentarioTicketRepository comentarioRepository;
    private final TicketRepository ticketRepository;

    @Transactional(readOnly = true)
    public List<ComentarioTicket> obtenerHistorialDeTicket(Long ticketId) {
        return comentarioRepository.findByTicketIdOrderByCreadoEnAsc(ticketId);
    }

    @Transactional
    public ComentarioTicket agregarComentario(ComentarioTicket comentario) {
        // Validamos que el ticket exista antes de comentar
        Ticket ticket = ticketRepository.findById(comentario.getTicket().getId())
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        
        // Guardamos el comentario
        return comentarioRepository.save(comentario);
    }
}