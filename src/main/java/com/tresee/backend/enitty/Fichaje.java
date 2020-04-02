package com.tresee.backend.enitty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "fichaje")
public class Fichaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfichaje")
    private Long idfichaje;

    @Column(name = "dia_fichaje", nullable = false, columnDefinition = "DATE")
    private LocalDate diaFichaje;

    @Column(name = "hora_entrada", columnDefinition = "TIME")
    private LocalTime horaEntrada;

    @Column(name = "hora_salida", columnDefinition = "TIME")
    private LocalTime horaSalida;

    /*TODO Check que este correcto*/
    @JsonIgnore
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "usuario_idusuario"), name = "usuario_idusuario", nullable = false)
    private Usuario usuario;

    public Fichaje() {
    }

    /*
     * Getters & Setters
     * */
    public Long getIdfichaje() {
        return idfichaje;
    }

    public void setIdfichaje(Long idfichaje) {
        this.idfichaje = idfichaje;
    }

    public LocalDate getDiaFichaje() {
        return diaFichaje;
    }

    public void setDiaFichaje(LocalDate diaFichaje) {
        this.diaFichaje = diaFichaje;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Fichaje{" +
                "idfichaje=" + idfichaje +
                ", diaFichaje=" + diaFichaje +
                ", horaEntrada=" + horaEntrada +
                ", horaSalida=" + horaSalida +
                ", usuario=" + usuario +
                '}';
    }
}
