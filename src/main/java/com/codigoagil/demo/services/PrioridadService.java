package com.codigoagil.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codigoagil.demo.models.Prioridad;
import com.codigoagil.demo.repositories.PrioridadRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrioridadService {
    private final PrioridadRepository prioridadRepository;

    @Transactional(readOnly = true)
    public List<Prioridad> obtenerTodas() {
        return prioridadRepository.findAll();
    }
}