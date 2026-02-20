package com.codigoagil.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codigoagil.demo.models.ComentarioTicket;
import com.codigoagil.demo.models.Ticket;
import com.codigoagil.demo.services.ComentarioTicketService;
import com.codigoagil.demo.services.TicketService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final ComentarioTicketService comentarioService;

    // --- ENDPOINTS DE TICKETS ---

    @GetMapping
    public ResponseEntity<List<Ticket>> obtenerTodos() {
        return ResponseEntity.ok(ticketService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.obtenerPorId(id));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Ticket>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(ticketService.obtenerPorCliente(clienteId));
    }

    @PostMapping
    public ResponseEntity<Ticket> crearTicket(@RequestBody Ticket ticket) {
        Ticket nuevoTicket = ticketService.crearTicket(ticket);
        return new ResponseEntity<>(nuevoTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/asignar")
    public ResponseEntity<Ticket> asignarAgente(@PathVariable Long id, @RequestParam Long agenteId) {
        Ticket ticketActualizado = ticketService.asignarAgente(id, agenteId);
        return ResponseEntity.ok(ticketActualizado);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Ticket> cambiarEstado(@PathVariable Long id, @RequestParam Long estadoId) {
        Ticket ticketActualizado = ticketService.cambiarEstado(id, estadoId);
        return ResponseEntity.ok(ticketActualizado);
    }

    // --- ENDPOINTS DE COMENTARIOS ---

    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<ComentarioTicket>> obtenerComentarios(@PathVariable Long id) {
        return ResponseEntity.ok(comentarioService.obtenerHistorialDeTicket(id));
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<ComentarioTicket> agregarComentario(
            @PathVariable Long id, 
            @RequestBody ComentarioTicket comentario) {
        
        // Aseguramos que el comentario se asocie al ticket de la URL
        Ticket ticketReferencia = new Ticket();
        ticketReferencia.setId(id);
        comentario.setTicket(ticketReferencia);
        
        ComentarioTicket nuevoComentario = comentarioService.agregarComentario(comentario);
        return new ResponseEntity<>(nuevoComentario, HttpStatus.CREATED);
    }
}