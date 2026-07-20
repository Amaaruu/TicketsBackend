package com.codigoagil.demo.config;

import com.codigoagil.demo.models.Rol;
import com.codigoagil.demo.models.Usuario;
import com.codigoagil.demo.repositories.RolRepository;
import com.codigoagil.demo.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initData(RolRepository rolRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            
            Rol adminRole = rolRepository.findByNombre("ROLE_ADMIN").orElse(null);
            if (adminRole == null) {
                adminRole = new Rol();
                adminRole.setNombre("ROLE_ADMIN");
                adminRole = rolRepository.save(adminRole);
            }

            Rol userRole = rolRepository.findByNombre("ROLE_USER").orElse(null);
            if (userRole == null) {
                userRole = new Rol();
                userRole.setNombre("ROLE_USER");
                rolRepository.save(userRole);
            }

            if (usuarioRepository.findByEmail("admin@demo.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setEmail("admin@demo.com");
                admin.setPasswordHash(passwordEncoder.encode("1234"));
                admin.setActivo(true);
                admin.setCreadoEn(LocalDateTime.now());
                admin.setRol(adminRole);
                
                usuarioRepository.save(admin);
            }
        };
    }
}