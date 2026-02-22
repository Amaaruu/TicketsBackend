package com.codigoagil.demo.dtos;

public record LoginRequestDTO(
        String email,
        String password
) {}