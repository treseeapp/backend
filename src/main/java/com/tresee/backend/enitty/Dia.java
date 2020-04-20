package com.tresee.backend.enitty;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tresee.backend.enitty.enums.DiaDeLaSemana;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "dia")
public class Dia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddia")
    private Long iddia;

    @Column(name = "nombre", nullable = false, columnDefinition = "tinyint")
    private DiaDeLaSemana dia;

    /*TODO Check que este correcto*/
    @JsonIgnore
    @OneToMany(mappedBy = "dia", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<EmpresaTieneDia> intermedia;

    public Dia() {
    }

    public Long getIddia() {
        return iddia;
    }

    public void setIddia(Long iddia) {
        this.iddia = iddia;
    }

    public DiaDeLaSemana getDia() {
        return dia;
    }

    public void setDia(DiaDeLaSemana dia) {
        this.dia = dia;
    }

    public List<EmpresaTieneDia> getIntermedia() {
        return intermedia;
    }

    public void setIntermedia(List<EmpresaTieneDia> intermedia) {
        this.intermedia = intermedia;
    }
}
