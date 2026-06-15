package com.codigoagil.demo.controllers;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codigoagil.demo.models.Rol;
import com.codigoagil.demo.services.RolService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
public class CatalogoController {

    private final RolService rolService;

    @GetMapping("/roles")
    public ResponseEntity<List<Rol>> obtenerRoles() {
        return ResponseEntity.ok(rolService.obtenerTodas());
    }
}