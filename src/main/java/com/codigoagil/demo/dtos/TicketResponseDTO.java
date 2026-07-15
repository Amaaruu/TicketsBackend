package com.codigoagil.demo.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class TicketResponseDTO {

    private Long id;
    private String asunto;
    private String descripcion;
    private String estado;
    private LocalDateTime fechaCreacion;
    private String emailCliente;
    private String nombreCliente;
    private List<ArchivoAdjuntoResponseDTO> adjuntos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<ArchivoAdjuntoResponseDTO> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<ArchivoAdjuntoResponseDTO> adjuntos) {
        this.adjuntos = adjuntos;
    }
}