package com.codigoagil.demo.controllers;

import com.codigoagil.demo.dtos.TicketResponseDTO;
import com.codigoagil.demo.services.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> listarTickets() {
        return ResponseEntity.ok(ticketService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> obtenerTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.obtenerPorId(id));
    }
}