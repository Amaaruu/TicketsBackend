package com.codigoagil.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codigoagil.demo.models.Usuario;
import com.codigoagil.demo.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El correo electrónico ya está registrado.");
        }
        return usuarioRepository.save(usuario);
    }

    // PUT: Actualiza todos los campos
    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario usuarioDetalles) {
        Usuario usuario = obtenerPorId(id);
        usuario.setNombre(usuarioDetalles.getNombre());
        usuario.setEmail(usuarioDetalles.getEmail());
        usuario.setRol(usuarioDetalles.getRol());
        usuario.setActivo(usuarioDetalles.getActivo());
        // La contraseña se actualizaría en otro endpoint específico por seguridad
        return usuarioRepository.save(usuario);
    }

    // PATCH: Actualiza solo los campos que se envían (no nulos)
    @Transactional
    public Usuario actualizarParcialUsuario(Long id, Usuario usuarioDetalles) {
        Usuario usuario = obtenerPorId(id);
        
        if (usuarioDetalles.getNombre() != null) usuario.setNombre(usuarioDetalles.getNombre());
        if (usuarioDetalles.getEmail() != null) usuario.setEmail(usuarioDetalles.getEmail());
        if (usuarioDetalles.getRol() != null) usuario.setRol(usuarioDetalles.getRol());
        if (usuarioDetalles.getActivo() != null) usuario.setActivo(usuarioDetalles.getActivo());
        
        return usuarioRepository.save(usuario);
    }

    // DELETE Lógico: No borra de BD, solo desactiva
    @Transactional
    public void eliminarUsuario(Long id) {
        Usuario usuario = obtenerPorId(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}