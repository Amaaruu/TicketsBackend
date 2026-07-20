package com.codigoagil.demo.dtos;

public class LoginRequestDTO {
    private String email; // Debe llamarse email, no username
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}