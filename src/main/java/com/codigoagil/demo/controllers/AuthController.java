package com.codigoagil.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        
        // 1. Buscamos al usuario por correo
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElse(null);

        // 2. Validamos que el usuario exista y la contraseña coincida
        if (usuario == null || !passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }

        // 3. Validamos que el usuario no esté desactivado
        if (!usuario.getActivo()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Usuario desactivado");
        }

        // 4. Generamos el Token y lo devolvemos
        String token = jwtUtil.generarToken(usuario.getEmail(), usuario.getRol().getNombre());
        AuthResponseDTO response = new AuthResponseDTO(token, usuario.getEmail(), usuario.getRol().getNombre());
        
        return ResponseEntity.ok(response);
    }
}