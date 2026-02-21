package com.codigoagil.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
        return new ResponseEntity<>(ticketService.crearTicket(ticket), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> actualizarTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.actualizarTicket(id, ticket));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Ticket> actualizarParcialTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        return ResponseEntity.ok(ticketService.actualizarParcialTicket(id, ticket));
    }

    @PutMapping("/{id}/asignar")
    public ResponseEntity<Ticket> asignarAgente(@PathVariable Long id, @RequestParam Long agenteId) {
        return ResponseEntity.ok(ticketService.asignarAgente(id, agenteId));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Ticket> cambiarEstado(@PathVariable Long id, @RequestParam Long estadoId) {
        return ResponseEntity.ok(ticketService.cambiarEstado(id, estadoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTicket(@PathVariable Long id) {
        ticketService.eliminarTicket(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS DE COMENTARIOS ANIDADOS ---

    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<ComentarioTicket>> obtenerComentarios(@PathVariable Long id) {
        return ResponseEntity.ok(comentarioService.obtenerHistorialDeTicket(id));
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<ComentarioTicket> agregarComentario(@PathVariable Long id, @RequestBody ComentarioTicket comentario) {
        Ticket ticketReferencia = new Ticket();
        ticketReferencia.setId(id);
        comentario.setTicket(ticketReferencia);
        return new ResponseEntity<>(comentarioService.agregarComentario(comentario), HttpStatus.CREATED);
    }

    @PatchMapping("/{ticketId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioTicket> editarComentario(@PathVariable Long ticketId, @PathVariable Long comentarioId, @RequestBody ComentarioTicket comentario) {
        // En un caso real aquí verificaríamos que el comentario pertenezca al ticketId
        return ResponseEntity.ok(comentarioService.actualizarComentario(comentarioId, comentario.getMensaje()));
    }

    @DeleteMapping("/{ticketId}/comentarios/{comentarioId}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long ticketId, @PathVariable Long comentarioId) {
        comentarioService.eliminarComentario(comentarioId);
        return ResponseEntity.noContent().build();
    }
}