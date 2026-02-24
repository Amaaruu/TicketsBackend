package com.codigoagil.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codigoagil.demo.models.ComentarioTicket;
import com.codigoagil.demo.models.Ticket;
import com.codigoagil.demo.models.Usuario;
import com.codigoagil.demo.repositories.ComentarioTicketRepository;
import com.codigoagil.demo.repositories.TicketRepository;
import com.codigoagil.demo.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComentarioTicketService {

    private final ComentarioTicketRepository comentarioRepository;
    private final TicketRepository ticketRepository;
    private final UsuarioRepository usuarioRepository; // <-- AÑADIDO: Para buscar al usuario real

    @Transactional(readOnly = true)
    public List<ComentarioTicket> obtenerHistorialDeTicket(Long ticketId) {
        return comentarioRepository.findByTicketIdOrderByCreadoEnAsc(ticketId);
    }

    @Transactional
    public ComentarioTicket agregarComentario(ComentarioTicket comentario) {
        // 1. Buscamos el ticket real en la BD para asegurarnos de que exista
        Ticket ticket = ticketRepository.findById(comentario.getTicket().getId())
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
        comentario.setTicket(ticket);
        
        // 2. Buscamos al usuario real para que tenga su nombre y rol listos para el DTO
        Usuario usuario = usuarioRepository.findById(comentario.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        comentario.setUsuario(usuario);

        // 3. Ahora sí, guardamos y retornamos el objeto completamente lleno
        return comentarioRepository.save(comentario);
    }

    // PUT/PATCH: Normalmente un comentario solo actualiza su texto
    @Transactional
    public ComentarioTicket actualizarComentario(Long id, String nuevoMensaje) {
        ComentarioTicket comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
        comentario.setMensaje(nuevoMensaje);
        return comentarioRepository.save(comentario);
    }

    // DELETE: Borrar un comentario específico
    @Transactional
    public void eliminarComentario(Long id) {
        if (!comentarioRepository.existsById(id)) {
            throw new RuntimeException("Comentario no encontrado");
        }
        comentarioRepository.deleteById(id);
    }
}