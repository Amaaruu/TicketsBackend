package com.codigoagil.demo.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.codigoagil.demo.models.Usuario;
import com.codigoagil.demo.repositories.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));

        // Convertimos nuestro Usuario a un UserDetails de Spring Security
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPasswordHash())
                // Spring Security usa el prefijo ROLE_ internamente
                .roles(usuario.getRol().getNombre()) 
                .build();
    }
}