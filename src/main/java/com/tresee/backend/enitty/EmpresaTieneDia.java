package com.tresee.backend.enitty;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;

@Entity
@Table(name = "empresa_has_dia")
public class EmpresaTieneDia implements Serializable {

    /*TODO Check que este correcto*/

    @Id
    @ManyToOne
    @JoinColumn(name = "empresa_idempresa", nullable = false)
    private Empresa empresa;

    @Id
    @ManyToOne
    @JoinColumn(name = "dia_iddia", nullable = false)
    private Dia dia;

    @Column(name = "hora_entrada", columnDefinition = "TIME")
    private Time horaEntrada;

    @Column(name = "hora_salida", columnDefinition = "TIME")
    private Time horaSalida;

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
