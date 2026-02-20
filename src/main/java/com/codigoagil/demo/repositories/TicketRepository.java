package com.codigoagil.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codigoagil.demo.models.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    // Si eres un Cliente, querrás ver solo TUS tickets
    List<Ticket> findByClienteId(Long clienteId);

    // Si eres un Agente, querrás ver los tickets que te asignaron
    List<Ticket> findByAgenteId(Long agenteId);

    // Para un dashboard: "Muéstrame todos los tickets en estado Abierto"
    List<Ticket> findByEstadoId(Long estadoId);
}