package com.tresee.backend.enitty.modelCsv;

import com.opencsv.bean.CsvBindByName;

public class UsuarioCSV {
    @CsvBindByName
    private String nombre;

    @CsvBindByName
    private String email;

    @CsvBindByName
    private String apellidos;

    public UsuarioCSV() {
    }

    public UsuarioCSV(String nombre, String email, String apellidos) {
        this.nombre = nombre;
        this.email = email;
        this.apellidos = apellidos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
}
