package com.tresee.backend.enitty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    /*TODO Check que este correcto*/
    @OneToMany(mappedBy = "empresa", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<EmpresaTieneDia> empresaTieneDias;

    @OneToMany(mappedBy = "empresa", orphanRemoval = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Usuario> estudiantes;

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
}
