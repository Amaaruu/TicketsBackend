package com.codigoagil.demo.dtos;

public class CorreoAdjuntoDTO {

    private String nombreOriginal;
    private String tipoMime;
    private byte[] contenido;

    public CorreoAdjuntoDTO() {}

    public CorreoAdjuntoDTO(String nombreOriginal, String tipoMime, byte[] contenido) {
        this.nombreOriginal = nombreOriginal;
        this.tipoMime = tipoMime;
        this.contenido = contenido;
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

    public byte[] getContenido() {
        return contenido;
    }

    public void setContenido(byte[] contenido) {
        this.contenido = contenido;
    }
}