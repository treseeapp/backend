package com.tresee.backend.enitty;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;

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
    private Time horaEntrada;

    @Column(name = "hora_salida", columnDefinition = "TIME")
    private Time horaSalida;


    /*TODO Check que este correcto*/
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "usuario_idusuario"), name = "usuario_idusuario", nullable = true)
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

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }
}
