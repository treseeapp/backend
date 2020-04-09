package com.tresee.backend.controller;

import com.tresee.backend.enitty.EmpresaTieneDia;
import com.tresee.backend.manager.HorarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HorariosController {

    @Autowired
    private HorarioManager horarioManager;

    @PostMapping("/admin/horario")
    @Transactional
    public ResponseEntity<String> createSchedule(@RequestBody String json) {
        EmpresaTieneDia horario = this.horarioManager.fromJson(json);
        if (horario.getEmpresa() == null) {

            return new ResponseEntity<>("La empresa ha de existir", HttpStatus.BAD_REQUEST);

        } else if (horario.getDia() == null) {

            return new ResponseEntity<>("El dia ha de existir", HttpStatus.BAD_REQUEST);

        } else if (horario.getHoraEntrada() == null) {

            return new ResponseEntity<>("El horario de entrada es obligatorio", HttpStatus.BAD_REQUEST);

        } else if (horario.getHoraSalida() == null) {

            return new ResponseEntity<>("El horario de salida es obligatorio", HttpStatus.BAD_REQUEST);

        } else if (horario.getHoraEntrada().isAfter(horario.getHoraSalida())) {

            return new ResponseEntity<>("La hora de entrada no puede ser mas tarde que la de salida", HttpStatus.BAD_REQUEST);

        }

        if (this.horarioManager.findByDiaAndEmpresa(horario.getDia(), horario.getEmpresa()) != null) {

            return new ResponseEntity<>("Ya hay un horario para esta empresa, con este dia", HttpStatus.BAD_REQUEST);

        }
        this.horarioManager.guardarHorario(horario);
        return new ResponseEntity<>("Horario creado correctamente", HttpStatus.OK);
    }
}
