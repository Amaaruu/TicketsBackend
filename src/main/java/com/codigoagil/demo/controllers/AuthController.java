package com.codigoagil.demo.controllers;

import com.codigoagil.demo.dtos.AuthResponseDTO;
import com.codigoagil.demo.dtos.LoginRequestDTO;
import com.codigoagil.demo.models.Usuario;
import com.codigoagil.demo.repositories.UsuarioRepository;
import com.codigoagil.demo.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioRepository usuarioRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            // 1. Intentar autenticar con email y password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // 2. Si es exitoso, extraer los detalles de la sesión
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            
            // 3. Buscar al usuario en la BD para obtener su ID y Rol real
            Usuario usuario = usuarioRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));
            
            String rolNombre = usuario.getRol().getNombre();

            // 4. Generar el token (ahora pasando los DOS parámetros que exige JwtUtil)
            String token = jwtUtil.generarToken(usuario.getEmail(), rolNombre);

            // 5. Retornar el DTO completo
            return ResponseEntity.ok(new AuthResponseDTO(token, usuario.getId(), usuario.getEmail(), rolNombre));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }
}