        package com.codigoagil.demo.dtos;

public class ArchivoAdjuntoResponseDTO {

    private Long id;
    private String nombreOriginal;
    private String tipoMime;

    public ArchivoAdjuntoResponseDTO(Long id, String nombreOriginal, String tipoMime) {
        this.id = id;
        this.nombreOriginal = nombreOriginal;
        this.tipoMime = tipoMime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreOriginal() {
        return nombreOriginal;
    }

    public void setNombreOriginal(String nombreOriginal) {
        this.nombreOriginal = nombreOriginal;
    }

    public String getTipoMime() {
        return tipoMime;
    }

    public void setTipoMime(String tipoMime) {
        this.tipoMime = tipoMime;
    }
}