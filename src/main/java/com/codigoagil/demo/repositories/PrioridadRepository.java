package com.codigoagil.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codigoagil.demo.models.Prioridad;

public interface PrioridadRepository extends JpaRepository<Prioridad, Long> {
}