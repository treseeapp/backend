package com.tresee.backend.controller;

import com.tresee.backend.enitty.EmpresaTieneDia;
import com.tresee.backend.manager.HorarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/admin/horario")
    @Transactional
    public ResponseEntity<String> updateSchedule(@RequestBody String json) {
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

        EmpresaTieneDia horarioToUpdate = this.horarioManager.findByDiaAndEmpresa(horario.getDia(), horario.getEmpresa());
        if (horarioToUpdate == null) {
            return new ResponseEntity<>("No hay ningun horario para esta empresa, con este dia para actualizar", HttpStatus.BAD_REQUEST);
        }
        horarioToUpdate.setHoraEntrada(horario.getHoraEntrada());
        horarioToUpdate.setHoraSalida(horario.getHoraSalida());
        this.horarioManager.update(horarioToUpdate);

        return new ResponseEntity<>("Horario creado correctamente", HttpStatus.OK);
    }

    @DeleteMapping("/admin/horario")
    @Transactional
    public ResponseEntity<String> deleteSchedule(@RequestBody String json) {
        EmpresaTieneDia horario = this.horarioManager.fromJson(json);
        if (horario.getEmpresa() == null) {
            return new ResponseEntity<>("La empresa ha de existir", HttpStatus.BAD_REQUEST);
        } else if (horario.getDia() == null) {
            return new ResponseEntity<>("El dia ha de existir", HttpStatus.BAD_REQUEST);
        }

        EmpresaTieneDia toDelete = this.horarioManager.findByDiaAndEmpresa(horario.getDia(), horario.getEmpresa());
        if (toDelete == null) {
            return new ResponseEntity<>("No hay ningun horario para esta empresa, con este dia para eliminar", HttpStatus.BAD_REQUEST);
        }

        this.horarioManager.delete(toDelete);

        return new ResponseEntity<>("Horario eliminado correctamente", HttpStatus.OK);
    }
}
