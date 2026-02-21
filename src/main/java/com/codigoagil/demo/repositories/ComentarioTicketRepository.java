package com.codigoagil.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codigoagil.demo.models.ComentarioTicket;

public interface ComentarioTicketRepository extends JpaRepository<ComentarioTicket, Long> {
    
    List<ComentarioTicket> findByTicketIdOrderByCreadoEnAsc(Long ticketId);

    // Eliminación en cascada manual
    @Modifying
    @Query("DELETE FROM ComentarioTicket c WHERE c.ticket.id = :ticketId")
    void deleteByTicketId(@Param("ticketId") Long ticketId);
}