package com.tresee.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tresee.backend.enitty.Empresa;
import com.tresee.backend.enitty.Fichaje;
import com.tresee.backend.enitty.enums.Genero;
import com.tresee.backend.enitty.enums.ModoInicioSesion;
import com.tresee.backend.enitty.enums.Rol;

import java.time.LocalDate;
import java.util.List;

public class UsuarioConEmpresa {

    private Long idusuario;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String email;
    private LocalDate dataNacimiento;
    @JsonIgnore
    private String contraseña;
    private Rol rol;
    private Genero genero;
    private ModoInicioSesion modoInicioSesion;
    private String fotoPerfil;
    private Empresa empresa;
    private List<Fichaje> fichajes;

    public UsuarioConEmpresa() {
    }

    public Long getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Long idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNacimiento() {
        return dataNacimiento;
    }

    public void setDataNacimiento(LocalDate dataNacimiento) {
        this.dataNacimiento = dataNacimiento;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public ModoInicioSesion getModoInicioSesion() {
        return modoInicioSesion;
    }

    public void setModoInicioSesion(ModoInicioSesion modoInicioSesion) {
        this.modoInicioSesion = modoInicioSesion;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Fichaje> getFichajes() {
        return fichajes;
    }

    public void setFichajes(List<Fichaje> fichajes) {
        this.fichajes = fichajes;
    }
}
