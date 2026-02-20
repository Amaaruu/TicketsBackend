package com.codigoagil.demo.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codigoagil.demo.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Fundamental para el proceso de Login
    Optional<Usuario> findByEmail(String email);
    
    // Para validar si un correo ya está registrado antes de crear uno nuevo
    boolean existsByEmail(String email);
}