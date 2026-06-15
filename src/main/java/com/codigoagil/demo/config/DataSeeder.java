package com.codigoagil.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.codigoagil.demo.models.Rol;
import com.codigoagil.demo.models.Usuario;
import com.codigoagil.demo.repositories.RolRepository;
import com.codigoagil.demo.repositories.UsuarioRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(
            RolRepository rolRepo, 
            UsuarioRepository usuarioRepo,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (rolRepo.count() == 0) {
                Rol admin = new Rol(); admin.setNombre("ADMINISTRADOR");
                Rol usuario = new Rol(); usuario.setNombre("USUARIO");
                rolRepo.save(admin);
                rolRepo.save(usuario);
            }

            if (usuarioRepo.count() == 0) {
                Rol adminRol = rolRepo.findByNombre("ADMINISTRADOR").orElseThrow();
                String passwordEncriptada = passwordEncoder.encode("admin123");

                Usuario admin = new Usuario();
                admin.setNombre("Super Administrador");
                admin.setEmail("admin@admin.com");
                admin.setPasswordHash(passwordEncriptada);
                admin.setRol(adminRol);
                usuarioRepo.save(admin);
            }
        };
    }
}