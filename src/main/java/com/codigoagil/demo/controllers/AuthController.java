package com.codigoagil.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.codigoagil.demo.dtos.AuthResponseDTO;
import com.codigoagil.demo.dtos.LoginRequestDTO;
import com.codigoagil.demo.models.Usuario;
import com.codigoagil.demo.repositories.UsuarioRepository;
import com.codigoagil.demo.security.JwtUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElse(null);

        if (usuario == null || !passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }

        if (!usuario.getActivo()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario desactivado");
        }

        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().getNombre());
        
        // AQUÍ ESTÁ EL CAMBIO: Pasamos usuario.getId() como segundo parámetro
        AuthResponseDTO response = new AuthResponseDTO(
                token, 
                usuario.getId(), 
                usuario.getEmail(), 
                usuario.getRol().getNombre()
        );
        
        return ResponseEntity.ok(response);
    }
}