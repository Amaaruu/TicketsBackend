package com.codigoagil.demo.models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titulo;

    // columnDefinition = "TEXT" permite guardar textos largos (mucho más de 255 caracteres)
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    // Relación con Usuario (El que crea el ticket)
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Usuario cliente;

    // Relación con Usuario (El que resuelve el ticket). Puede ser null si aún no se asigna.
    @ManyToOne
    @JoinColumn(name = "agente_id")
    private Usuario agente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prioridad_id", nullable = false)
    private Prioridad prioridad;

    @CreationTimestamp
    @Column(name = "creado_en", updatable = false)
    private LocalDateTime creadoEn;

    // Hibernate actualizará esta fecha automáticamente cada vez que modifiques el ticket
    @UpdateTimestamp
    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;
}