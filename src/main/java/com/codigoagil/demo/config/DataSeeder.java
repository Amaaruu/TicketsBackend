package com.codigoagil.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codigoagil.demo.models.Estado;
import com.codigoagil.demo.models.Prioridad;
import com.codigoagil.demo.models.Rol;
import com.codigoagil.demo.repositories.EstadoRepository;
import com.codigoagil.demo.repositories.PrioridadRepository;
import com.codigoagil.demo.repositories.RolRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(RolRepository rolRepo, EstadoRepository estadoRepo, PrioridadRepository prioridadRepo) {
        return args -> {
            // Inicializar Roles
            if (rolRepo.count() == 0) {
                Rol admin = new Rol(); admin.setNombre("ADMINISTRADOR");
                Rol agente = new Rol(); agente.setNombre("AGENTE");
                Rol cliente = new Rol(); cliente.setNombre("CLIENTE");
                rolRepo.save(admin);
                rolRepo.save(agente);
                rolRepo.save(cliente);
            }

            // Inicializar Estados
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

            // Inicializar Prioridades
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
        };
    }
}