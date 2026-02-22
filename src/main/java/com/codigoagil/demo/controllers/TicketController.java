package com.codigoagil.demo.controllers;

import java.util.List;
import java.util.stream.Collectors;

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

import com.codigoagil.demo.dtos.ComentarioResponseDTO;
import com.codigoagil.demo.dtos.TicketResponseDTO;
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

    // Mappers
    private TicketResponseDTO convertirTicketADTO(Ticket t) {
        String agenteNombre = (t.getAgente() != null) ? t.getAgente().getNombre() : "Sin Asignar";
        
        return new TicketResponseDTO(
                t.getId(),
                t.getTitulo(),
                t.getDescripcion(),
                t.getCliente().getNombre(),
                agenteNombre,
                t.getCategoria().getNombre(),
                t.getEstado().getNombre(),
                t.getPrioridad().getNombre(),
                t.getCreadoEn(),
                t.getActualizadoEn()
        );
    }

    private ComentarioResponseDTO convertirComentarioADTO(ComentarioTicket c) {
        return new ComentarioResponseDTO(
                c.getId(),
                c.getUsuario().getNombre(),
                c.getUsuario().getRol().getNombre(),
                c.getMensaje(),
                c.getCreadoEn()
        );
    }

    // --- ENDPOINTS DE TICKETS ---

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(ticketService.obtenerTodos().stream()
                .map(this::convertirTicketADTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(convertirTicketADTO(ticketService.obtenerPorId(id)));
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<TicketResponseDTO>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(ticketService.obtenerPorCliente(clienteId).stream()
                .map(this::convertirTicketADTO).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> crearTicket(@RequestBody Ticket ticket) {
        return new ResponseEntity<>(convertirTicketADTO(ticketService.crearTicket(ticket)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> actualizarTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        return ResponseEntity.ok(convertirTicketADTO(ticketService.actualizarTicket(id, ticket)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> actualizarParcialTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        return ResponseEntity.ok(convertirTicketADTO(ticketService.actualizarParcialTicket(id, ticket)));
    }

    @PutMapping("/{id}/asignar")
    public ResponseEntity<TicketResponseDTO> asignarAgente(@PathVariable Long id, @RequestParam Long agenteId) {
        return ResponseEntity.ok(convertirTicketADTO(ticketService.asignarAgente(id, agenteId)));
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<TicketResponseDTO> cambiarEstado(@PathVariable Long id, @RequestParam Long estadoId) {
        return ResponseEntity.ok(convertirTicketADTO(ticketService.cambiarEstado(id, estadoId)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTicket(@PathVariable Long id) {
        ticketService.eliminarTicket(id);
        return ResponseEntity.noContent().build();
    }

    // --- ENDPOINTS DE COMENTARIOS ---

    @GetMapping("/{id}/comentarios")
    public ResponseEntity<List<ComentarioResponseDTO>> obtenerComentarios(@PathVariable Long id) {
        return ResponseEntity.ok(comentarioService.obtenerHistorialDeTicket(id).stream()
                .map(this::convertirComentarioADTO).collect(Collectors.toList()));
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<ComentarioResponseDTO> agregarComentario(@PathVariable Long id, @RequestBody ComentarioTicket comentario) {
        Ticket ticketReferencia = new Ticket();
        ticketReferencia.setId(id);
        comentario.setTicket(ticketReferencia);
        return new ResponseEntity<>(convertirComentarioADTO(comentarioService.agregarComentario(comentario)), HttpStatus.CREATED);
    }

    @PatchMapping("/{ticketId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioResponseDTO> editarComentario(@PathVariable Long ticketId, @PathVariable Long comentarioId, @RequestBody ComentarioTicket comentario) {
        return ResponseEntity.ok(convertirComentarioADTO(comentarioService.actualizarComentario(comentarioId, comentario.getMensaje())));
    }

    @DeleteMapping("/{ticketId}/comentarios/{comentarioId}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Long ticketId, @PathVariable Long comentarioId) {
        comentarioService.eliminarComentario(comentarioId);
        return ResponseEntity.noContent().build();
    }
}