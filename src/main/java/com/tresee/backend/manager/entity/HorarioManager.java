package com.tresee.backend.manager.entity;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private Pattern pattern;

    public HorarioManager() {
        this.pattern = Pattern.compile(TIME24HOURS_PATTERN);
    }

    public void guardarHorario(EmpresaTieneDia horario) {
        this.empresaTieneDiaRepository.save(horario);
    }

    public EmpresaTieneDia findByDiaAndEmpresa(Dia dia, Empresa empresa) {
        return this.empresaTieneDiaRepository.findByDiaAndEmpresa(dia, empresa);
    }

    public EmpresaTieneDia fromJson(String json) {
        Matcher matcher;
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
            matcher = pattern.matcher(time);
            if (!time.equals("") && matcher.matches()) {
                horario.setHoraEntrada(LocalTime.parse(time));
            }
        }
        if (jsonObject.get("horaSalida") != null) {
            String time = jsonObject.get("horaSalida").getAsString();
            matcher = pattern.matcher(time);
            if (!time.equals("") && matcher.matches()) {
                horario.setHoraSalida(LocalTime.parse(time));
            }
        }

        return horario;
    }

    public void update(EmpresaTieneDia horario) {
        this.empresaTieneDiaRepository.save(horario);
    }

    public void delete(EmpresaTieneDia toDelete) {
        this.empresaTieneDiaRepository.delete(toDelete);
    }
}
