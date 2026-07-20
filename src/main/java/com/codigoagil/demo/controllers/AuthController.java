package com.codigoagil.demo.controllers;

import com.codigoagil.demo.dtos.AuthResponseDTO;
import com.codigoagil.demo.dtos.LoginRequestDTO;
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
@RequestMapping("/api/auth") // Ruta estandarizada
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            // 1. Intentar autenticar con email y password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // 2. Si es exitoso, generar token
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtil.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new AuthResponseDTO(token));

        } catch (BadCredentialsException e) {
            // 3. Si las credenciales son incorrectas, devolver 401 limpio
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        } catch (Exception e) {
            // 4. Si falla cualquier otra cosa, devolver 500 controlado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }
}