package com.tresee.backend.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tresee.backend.enitty.Empresa;
import com.tresee.backend.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmpresaManager {
    @Autowired
    private Gson gson;

    @Autowired
    private EmpresaRepository empresaRepository;

    public void create(Empresa empresa) {
        empresaRepository.save(empresa);
    }

    public Empresa fromJsonCreate(String json) {
        Empresa empresa = new Empresa();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        if (jsonObject.get("nombre") != null) {
            empresa.setNombre(jsonObject.get("nombre").getAsString());
        }
        if (jsonObject.get("contacto") != null) {
            empresa.setContacto(jsonObject.get("contacto").getAsString());
        }
        if (jsonObject.get("fechaInicioPracticas") != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
            LocalDate date = LocalDate.parse(jsonObject.get("fechaInicioPracticas").getAsString(), formatter);
            empresa.setInicioPracticas(date);
        }

        return empresa;
    }
}
