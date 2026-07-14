package com.codigoagil.demo.services;

import com.codigoagil.demo.dtos.CorreoAdjuntoDTO;
import com.codigoagil.demo.models.ArchivoAdjunto;
import com.codigoagil.demo.models.Cliente;
import com.codigoagil.demo.models.Ticket;
import com.codigoagil.demo.repositories.ArchivoAdjuntoRepository;
import com.codigoagil.demo.repositories.ClienteRepository;
import com.codigoagil.demo.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ClienteRepository clienteRepository;
    private final ArchivoAdjuntoRepository archivoAdjuntoRepository;
    private final FileStorageService fileStorageService;

    public TicketService(TicketRepository ticketRepository, 
                         ClienteRepository clienteRepository, 
                         ArchivoAdjuntoRepository archivoAdjuntoRepository,
                         FileStorageService fileStorageService) {
        this.ticketRepository = ticketRepository;
        this.clienteRepository = clienteRepository;
        this.archivoAdjuntoRepository = archivoAdjuntoRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public Ticket crearTicketDesdeCorreo(String email, String nombre, String asunto, String descripcion, List<CorreoAdjuntoDTO> adjuntos) {
        
        Cliente cliente = clienteRepository.findByEmail(email).orElseGet(() -> {
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setEmail(email);
            nuevoCliente.setNombre(nombre);
            return clienteRepository.save(nuevoCliente);
        });

        Ticket ticket = new Ticket();
        ticket.setCliente(cliente);
        ticket.setAsunto(asunto);
        ticket.setDescripcion(descripcion);
        Ticket ticketGuardado = ticketRepository.save(ticket);

        if (adjuntos != null && !adjuntos.isEmpty()) {
            List<ArchivoAdjunto> archivos = new ArrayList<>();
            for (CorreoAdjuntoDTO adjuntoDTO : adjuntos) {
                String ruta = fileStorageService.storeFile(adjuntoDTO.getContenido(), adjuntoDTO.getNombreOriginal());
                
                ArchivoAdjunto archivo = new ArchivoAdjunto();
                archivo.setNombreOriginal(adjuntoDTO.getNombreOriginal());
                archivo.setTipoMime(adjuntoDTO.getTipoMime());
                archivo.setRutaAlmacenamiento(ruta);
                archivo.setTicket(ticketGuardado);
                archivos.add(archivo);
            }
            archivoAdjuntoRepository.saveAll(archivos);
            ticketGuardado.setArchivosAdjuntos(archivos);
        }

        return ticketGuardado;
    }
}