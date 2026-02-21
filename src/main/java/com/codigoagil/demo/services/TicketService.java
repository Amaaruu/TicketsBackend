package com.codigoagil.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codigoagil.demo.models.Estado;
import com.codigoagil.demo.models.Ticket;
import com.codigoagil.demo.models.Usuario;
import com.codigoagil.demo.repositories.ComentarioTicketRepository;
import com.codigoagil.demo.repositories.EstadoRepository;
import com.codigoagil.demo.repositories.TicketRepository;
import com.codigoagil.demo.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EstadoRepository estadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ComentarioTicketRepository comentarioRepository;

    @Transactional(readOnly = true)
    public List<Ticket> obtenerTodos() { return ticketRepository.findAll(); }

    @Transactional(readOnly = true)
    public List<Ticket> obtenerPorCliente(Long clienteId) { return ticketRepository.findByClienteId(clienteId); }

    @Transactional(readOnly = true)
    public List<Ticket> obtenerPorAgente(Long agenteId) { return ticketRepository.findByAgenteId(agenteId); }

    @Transactional(readOnly = true)
    public Ticket obtenerPorId(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
    }

    @Transactional
    public Ticket crearTicket(Ticket ticket) {
        Estado estadoInicial = estadoRepository.findByNombre("ABIERTO")
                .orElseThrow(() -> new RuntimeException("Estado inicial no encontrado"));
        ticket.setEstado(estadoInicial);
        ticket.setAgente(null); 
        return ticketRepository.save(ticket);
    }

    // PUT: Sobrescribir detalles principales del ticket
    @Transactional
    public Ticket actualizarTicket(Long id, Ticket detalles) {
        Ticket ticket = obtenerPorId(id);
        ticket.setTitulo(detalles.getTitulo());
        ticket.setDescripcion(detalles.getDescripcion());
        ticket.setCategoria(detalles.getCategoria());
        ticket.setPrioridad(detalles.getPrioridad());
        return ticketRepository.save(ticket);
    }

    // PATCH: Actualización parcial
    @Transactional
    public Ticket actualizarParcialTicket(Long id, Ticket detalles) {
        Ticket ticket = obtenerPorId(id);
        if (detalles.getTitulo() != null) ticket.setTitulo(detalles.getTitulo());
        if (detalles.getDescripcion() != null) ticket.setDescripcion(detalles.getDescripcion());
        if (detalles.getCategoria() != null) ticket.setCategoria(detalles.getCategoria());
        if (detalles.getPrioridad() != null) ticket.setPrioridad(detalles.getPrioridad());
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket asignarAgente(Long ticketId, Long agenteId) {
        Ticket ticket = obtenerPorId(ticketId);
        Usuario agente = usuarioRepository.findById(agenteId).orElseThrow(() -> new RuntimeException("Agente no encontrado"));
        Estado estadoProgreso = estadoRepository.findByNombre("EN PROGRESO").orElseThrow();
        ticket.setAgente(agente);
        ticket.setEstado(estadoProgreso);
        return ticketRepository.save(ticket);
    }
    
    @Transactional
    public Ticket cambiarEstado(Long ticketId, Long nuevoEstadoId) {
        Ticket ticket = obtenerPorId(ticketId);
        Estado nuevoEstado = estadoRepository.findById(nuevoEstadoId).orElseThrow();
        ticket.setEstado(nuevoEstado);
        return ticketRepository.save(ticket);
    }

    // DELETE Físico con Cascada: Borra comentarios primero y luego el ticket
    @Transactional
    public void eliminarTicket(Long id) {
        Ticket ticket = obtenerPorId(id);
        comentarioRepository.deleteByTicketId(ticket.getId()); // CASCADA
        ticketRepository.delete(ticket); // BORRADO FINAL
    }
}