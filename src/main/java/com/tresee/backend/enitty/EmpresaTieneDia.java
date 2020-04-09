package com.tresee.backend.enitty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;

@Entity
@Table(name = "empresa_has_dia")
public class EmpresaTieneDia implements Serializable {

    /*TODO Check que este correcto*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idempresa_tiene_dia")
    private Long idempresa_tiene_dia;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "empresa_idempresa")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "dia_iddia")
    private Dia dia;

    @Column(name = "hora_entrada", columnDefinition = "TIME")
    private LocalTime horaEntrada;

    @Column(name = "hora_salida", columnDefinition = "TIME")
    private LocalTime horaSalida;

    public EmpresaTieneDia() {
    }


    /*
     * Getters & Setters
     * */
    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public Long getIdempresa_tiene_dia() {
        return idempresa_tiene_dia;
    }

    public void setIdempresa_tiene_dia(Long idempresa_tiene_dia) {
        this.idempresa_tiene_dia = idempresa_tiene_dia;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }
}
