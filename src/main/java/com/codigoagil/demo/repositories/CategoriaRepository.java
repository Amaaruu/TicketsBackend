package com.codigoagil.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codigoagil.demo.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}