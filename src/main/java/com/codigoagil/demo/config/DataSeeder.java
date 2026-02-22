package com.codigoagil.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codigoagil.demo.models.Estado;
import com.codigoagil.demo.models.Prioridad;
import com.codigoagil.demo.models.Rol;
import com.codigoagil.demo.models.Usuario;
import com.codigoagil.demo.repositories.EstadoRepository;
import com.codigoagil.demo.repositories.PrioridadRepository;
import com.codigoagil.demo.repositories.RolRepository;
import com.codigoagil.demo.repositories.UsuarioRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(
            RolRepository rolRepo, 
            EstadoRepository estadoRepo, 
            PrioridadRepository prioridadRepo,
            UsuarioRepository usuarioRepo,
            PasswordEncoder passwordEncoder // Usamos el encriptador aquí también
    ) {
        return args -> {
            // 1. Inicializar Roles
            if (rolRepo.count() == 0) {
                Rol admin = new Rol(); admin.setNombre("ADMINISTRADOR");
                Rol agente = new Rol(); agente.setNombre("AGENTE");
                Rol cliente = new Rol(); cliente.setNombre("CLIENTE");
                rolRepo.save(admin);
                rolRepo.save(agente);
                rolRepo.save(cliente);
            }

            // 2. Inicializar Estados
            if (estadoRepo.count() == 0) {
                Estado abierto = new Estado(); abierto.setNombre("ABIERTO");
                Estado progreso = new Estado(); progreso.setNombre("EN PROGRESO");
                Estado resuelto = new Estado(); resuelto.setNombre("RESUELTO");
                Estado cerrado = new Estado(); cerrado.setNombre("CERRADO");
                estadoRepo.save(abierto);
                estadoRepo.save(progreso);
                estadoRepo.save(resuelto);
                estadoRepo.save(cerrado);
            }

            // 3. Inicializar Prioridades
            if (prioridadRepo.count() == 0) {
                Prioridad baja = new Prioridad(); baja.setNombre("BAJA");
                Prioridad media = new Prioridad(); media.setNombre("MEDIA");
                Prioridad alta = new Prioridad(); alta.setNombre("ALTA");
                Prioridad urgente = new Prioridad(); urgente.setNombre("URGENTE");
                prioridadRepo.save(baja);
                prioridadRepo.save(media);
                prioridadRepo.save(alta);
                prioridadRepo.save(urgente);
            }

            // 4. Inicializar Usuarios de Prueba (NUEVO)
            if (usuarioRepo.count() == 0) {
                Rol adminRol = rolRepo.findByNombre("ADMINISTRADOR").orElseThrow();
                Rol agenteRol = rolRepo.findByNombre("AGENTE").orElseThrow();
                Rol clienteRol = rolRepo.findByNombre("CLIENTE").orElseThrow();

                // Todos tendrán la misma contraseña para que sea fácil probar: "password123"
                String passwordEncriptada = passwordEncoder.encode("password123");

                Usuario admin = new Usuario();
                admin.setNombre("Super Administrador");
                admin.setEmail("admin@empresa.com");
                admin.setPasswordHash(passwordEncriptada);
                admin.setRol(adminRol);
                usuarioRepo.save(admin);

                Usuario agente = new Usuario();
                agente.setNombre("Agente Soporte");
                agente.setEmail("agente@empresa.com");
                agente.setPasswordHash(passwordEncriptada);
                agente.setRol(agenteRol);
                usuarioRepo.save(agente);

                Usuario cliente = new Usuario();
                cliente.setNombre("Cliente Juan");
                cliente.setEmail("juan@cliente.com");
                cliente.setPasswordHash(passwordEncriptada);
                cliente.setRol(clienteRol);
                usuarioRepo.save(cliente);
            }
        };
    }
}