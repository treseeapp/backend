package com.tresee.backend.enitty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "empresa")
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idempresa")
    private Long idempresa;

    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    @Column(name = "contacto", length = 300, nullable = false)
    private String contacto;

    @Column(name = "fecha_inicio_practicas", columnDefinition = "DATE")
    private LocalDate inicioPracticas;

    @Column(name = "direccion", length = 100)
    private String direccion;

    @Column(name = "foto_empresa")
    private String fotoEmpresa;

    /*TODO Check que este correcto*/
    @OneToMany(mappedBy = "empresa", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<EmpresaTieneDia> empresaTieneDias;

    @OneToMany(mappedBy = "empresa", orphanRemoval = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Usuario> estudiantes;

    public Empresa() {
    }

    /*
     * Getters & Setters
     * */
    public Long getIdempresa() {
        return idempresa;
    }

    public void setIdempresa(Long idempresa) {
        this.idempresa = idempresa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public LocalDate getInicioPracticas() {
        return inicioPracticas;
    }

    public void setInicioPracticas(LocalDate inicioPracticas) {
        this.inicioPracticas = inicioPracticas;
    }

    public String getFotoEmpresa() { return fotoEmpresa; }

    public void setFotoEmpresa(String fotoEmpresa) { this.fotoEmpresa = fotoEmpresa; }

    public List<EmpresaTieneDia> getEmpresaTieneDias() {
        return empresaTieneDias;
    }

    public void setEmpresaTieneDias(List<EmpresaTieneDia> empresaTieneDias) {
        this.empresaTieneDias = empresaTieneDias;
    }

    public Set<Usuario> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Set<Usuario> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "idempresa=" + idempresa +
                ", nombre='" + nombre + '\'' +
                ", contacto='" + contacto + '\'' +
                ", inicioPracticas=" + inicioPracticas +
                ", direccion='" + direccion + '\'' +
                ", fotoEmpresa='" + fotoEmpresa + '\'' +
                ", empresaTieneDias=" + empresaTieneDias +
                ", estudiantes=" + estudiantes +
                '}';
    }
}
