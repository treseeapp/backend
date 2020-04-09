package com.tresee.backend.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tresee.backend.enitty.Dia;
import com.tresee.backend.enitty.Empresa;
import com.tresee.backend.enitty.EmpresaTieneDia;
import com.tresee.backend.repository.DiaRepository;
import com.tresee.backend.repository.EmpresaRepository;
import com.tresee.backend.repository.EmpresaTieneDiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class HorarioManager {
    @Autowired
    private Gson gson;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private DiaRepository diaRepository;

    @Autowired
    private EmpresaTieneDiaRepository empresaTieneDiaRepository;

    public void guardarHorario(EmpresaTieneDia horario) {
        this.empresaTieneDiaRepository.save(horario);
    }

    public EmpresaTieneDia findByDiaAndEmpresa(Dia dia, Empresa empresa) {
        return this.empresaTieneDiaRepository.findByDiaAndEmpresa(dia, empresa);
    }

    public EmpresaTieneDia fromJson(String json) {
        EmpresaTieneDia horario = new EmpresaTieneDia();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        if (jsonObject.get("idempresa") != null) {
            horario.setEmpresa(empresaRepository.findByIdempresa(jsonObject.get("idempresa").getAsLong()));
        }

        if (jsonObject.get("iddia") != null) {
            horario.setDia(diaRepository.findByIddia(jsonObject.get("iddia").getAsLong()));
        }

        if (jsonObject.get("horaEntrada") != null) {
            String time = jsonObject.get("horaEntrada").getAsString();
            if (!time.equals("")) {
                horario.setHoraEntrada(LocalTime.parse(time));
            }
        }
        if (jsonObject.get("horaSalida") != null) {
            String time = jsonObject.get("horaSalida").getAsString();
            if (!time.equals("")) {
                horario.setHoraSalida(LocalTime.parse(time));
            }
        }

        return horario;
    }
}
