package com.codigoagil.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codigoagil.demo.models.Rol;
import com.codigoagil.demo.repositories.RolRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {
    private final RolRepository rolRepository;

    @Transactional(readOnly = true)
    public List<Rol> obtenerTodas() {
        return rolRepository.findAll();
    }
}