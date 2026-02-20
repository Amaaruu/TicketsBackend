package com.codigoagil.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codigoagil.demo.models.Estado;
import com.codigoagil.demo.models.Ticket;
import com.codigoagil.demo.models.Usuario;
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

    @Transactional(readOnly = true)
    public List<Ticket> obtenerTodos() {
        return ticketRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Ticket> obtenerPorCliente(Long clienteId) {
        return ticketRepository.findByClienteId(clienteId);
    }

    @Transactional(readOnly = true)
    public List<Ticket> obtenerPorAgente(Long agenteId) {
        return ticketRepository.findByAgenteId(agenteId);
    }

    @Transactional(readOnly = true)
    public Ticket obtenerPorId(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket no encontrado"));
    }

    @Transactional
    public Ticket crearTicket(Ticket ticket) {
        // Regla de negocio: Todo ticket nuevo nace con el estado "ABIERTO"
        Estado estadoInicial = estadoRepository.findByNombre("ABIERTO")
                .orElseThrow(() -> new RuntimeException("Estado inicial no encontrado en BD"));
        
        ticket.setEstado(estadoInicial);
        // Al crearse, aún no tiene agente asignado
        ticket.setAgente(null); 
        
        return ticketRepository.save(ticket);
    }

    @Transactional
    public Ticket asignarAgente(Long ticketId, Long agenteId) {
        Ticket ticket = obtenerPorId(ticketId);
        Usuario agente = usuarioRepository.findById(agenteId)
                .orElseThrow(() -> new RuntimeException("Agente no encontrado"));
        
        // Regla de negocio: Al asignar un agente, el estado pasa a "EN PROGRESO"
        Estado estadoProgreso = estadoRepository.findByNombre("EN PROGRESO")
                .orElseThrow(() -> new RuntimeException("Estado no encontrado en BD"));

        ticket.setAgente(agente);
        ticket.setEstado(estadoProgreso);
        
        return ticketRepository.save(ticket);
    }
    
    @Transactional
    public Ticket cambiarEstado(Long ticketId, Long nuevoEstadoId) {
        Ticket ticket = obtenerPorId(ticketId);
        Estado nuevoEstado = estadoRepository.findById(nuevoEstadoId)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
                
        ticket.setEstado(nuevoEstado);
        return ticketRepository.save(ticket);
    }
}