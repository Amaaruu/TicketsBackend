package com.codigoagil.demo.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codigoagil.demo.models.Categoria;
import com.codigoagil.demo.models.Estado;
import com.codigoagil.demo.models.Prioridad;
import com.codigoagil.demo.models.Rol;
import com.codigoagil.demo.services.CategoriaService;
import com.codigoagil.demo.services.EstadoService;
import com.codigoagil.demo.services.PrioridadService;
import com.codigoagil.demo.services.RolService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
public class CatalogoController {

    private final RolService rolService;
    private final EstadoService estadoService;
    private final PrioridadService prioridadService;
    private final CategoriaService categoriaService;

    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> obtenerRoles() {
        return ResponseEntity.ok(rolService.obtenerTodas());
    }

    @GetMapping("/estados")
    public ResponseEntity<List<Estado>> obtenerEstados() {
        return ResponseEntity.ok(estadoService.obtenerTodos());
    }

    @GetMapping("/prioridades")
    public ResponseEntity<List<Prioridad>> obtenerPrioridades() {
        return ResponseEntity.ok(prioridadService.obtenerTodas());
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> obtenerCategorias() {
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }
}