package com.codigoagil.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codigoagil.demo.models.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
    Optional<Estado> findByNombre(String nombre);
}