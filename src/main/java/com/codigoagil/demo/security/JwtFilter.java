package com.codigoagil.demo.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Buscamos el token en la cabecera "Authorization"
        String authHeader = request.getHeader("Authorization");

        // 2. Si hay un token y empieza con "Bearer ", lo procesamos
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Quitamos la palabra "Bearer "

            // 3. Si el token es válido y aún no hay nadie autenticado en este hilo
            if (jwtUtil.validarToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                
                String email = jwtUtil.extraerEmail(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                // 4. Creamos el pase de acceso oficial para Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                
                // 5. Autenticamos al usuario
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // 6. Dejamos que la petición continúe su camino
        filterChain.doFilter(request, response);
    }
}