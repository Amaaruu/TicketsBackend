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
            
            Rol adminRole = rolRepository.findByNombre("ROLE_ADMIN").orElseGet(() -> {
                Rol rol = new Rol();
                rol.setNombre("ROLE_ADMIN");
                return rolRepository.save(rol);
            });

            rolRepository.findByNombre("ROLE_USER").orElseGet(() -> {
                Rol rol = new Rol();
                rol.setNombre("ROLE_USER");
                return rolRepository.save(rol);
            });

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