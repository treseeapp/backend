package com.tresee.backend.enitty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tresee.backend.enitty.enums.Genero;
import com.tresee.backend.enitty.enums.ModoInicioSesion;
import com.tresee.backend.enitty.enums.Rol;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Long idusuario;

    @Column(name = "nombre", length = 30, nullable = false)
    private String nombre;

    @Column(name = "apellidos", length = 60, nullable = false)
    private String apellidos;

    @Column(name = "direccion", length = 60)
    private String direccion;

    @Column(name = "email", length = 80, nullable = false)
    private String email;

    @Column(name = "data_nacimiento", columnDefinition = "DATE")
    private LocalDate dataNacimiento;

    @JsonIgnore
    @Column(name = "contraseña", length = 300, nullable = false)
    private String contraseña;

    @Column(name = "rol", nullable = false, columnDefinition = "tinyint")
    private Rol rol;

    @Column(name = "genero", columnDefinition = "tinyint")
    private Genero genero;

    /*
     * Local - Google - Facebook ......
     * */
    @Column(name = "modo_inicio_sesion", columnDefinition = "tinyint")
    private ModoInicioSesion modoInicioSesion;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "empresa_idempresa"), name = "empresa_idempresa")
    private Empresa empresa;

    @OneToMany(mappedBy = "usuario", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Fichaje> fichajes;


    public Usuario() {
    }

    /*
     * Getters & Setters
     * */

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

    public List<Fichaje> getFichajes() {
        return fichajes;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setFichajes(List<Fichaje> fichajes) {
        this.fichajes = fichajes;
    }

    public void addFichaje(Fichaje fichaje) {
        this.fichajes.add(fichaje);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idusuario=" + idusuario +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", direccion='" + direccion + '\'' +
                ", email='" + email + '\'' +
                ", dataNacimiento=" + dataNacimiento +
                ", contraseña='" + contraseña + '\'' +
                ", rol=" + rol +
                ", genero=" + genero +
                ", modoInicioSesion=" + modoInicioSesion +
                ", fotoPerfil='" + fotoPerfil + '\'' +
                ", empresa=" + empresa +
                ", fichajes=" + fichajes +
                '}';
    }
}