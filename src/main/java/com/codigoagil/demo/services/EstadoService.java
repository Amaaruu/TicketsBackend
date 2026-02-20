package com.codigoagil.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codigoagil.demo.models.Estado;
import com.codigoagil.demo.repositories.EstadoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstadoService {
    private final EstadoRepository estadoRepository;

    @Transactional(readOnly = true)
    public List<Estado> obtenerTodos() {
        return estadoRepository.findAll();
    }
}