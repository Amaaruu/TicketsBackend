package com.codigoagil.demo.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codigoagil.demo.exceptions.BadRequestException;
import com.codigoagil.demo.exceptions.ResourceNotFoundException;
import com.codigoagil.demo.models.Usuario;
import com.codigoagil.demo.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Inyectamos el encriptador

    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    @Transactional
    public Usuario crearUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new BadRequestException("El correo electrónico ya está registrado.");
        }
        
        // Encriptamos la contraseña antes de guardarla en la base de datos
        String hash = passwordEncoder.encode(usuario.getPasswordHash());
        usuario.setPasswordHash(hash);
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizarUsuario(Long id, Usuario usuarioDetalles) {
        Usuario usuario = obtenerPorId(id);
        usuario.setNombre(usuarioDetalles.getNombre());
        usuario.setEmail(usuarioDetalles.getEmail());
        usuario.setRol(usuarioDetalles.getRol());
        usuario.setActivo(usuarioDetalles.getActivo());
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizarParcialUsuario(Long id, Usuario usuarioDetalles) {
        Usuario usuario = obtenerPorId(id);
        
        if (usuarioDetalles.getNombre() != null) usuario.setNombre(usuarioDetalles.getNombre());
        if (usuarioDetalles.getEmail() != null) usuario.setEmail(usuarioDetalles.getEmail());
        if (usuarioDetalles.getRol() != null) usuario.setRol(usuarioDetalles.getRol());
        if (usuarioDetalles.getActivo() != null) usuario.setActivo(usuarioDetalles.getActivo());
        
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        Usuario usuario = obtenerPorId(id);
        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }
}