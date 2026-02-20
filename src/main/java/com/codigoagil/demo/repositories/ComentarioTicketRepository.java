package com.codigoagil.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codigoagil.demo.models.ComentarioTicket;

public interface ComentarioTicketRepository extends JpaRepository<ComentarioTicket, Long> {
    // Para mostrar el chat de un ticket ordenado del más antiguo al más nuevo
    List<ComentarioTicket> findByTicketIdOrderByCreadoEnAsc(Long ticketId);
}