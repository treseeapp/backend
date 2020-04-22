package com.tresee.backend.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tresee.backend.enitty.Empresa;
import com.tresee.backend.enitty.EmpresaTieneDia;
import com.tresee.backend.enitty.Usuario;
import com.tresee.backend.repository.EmpresaRepository;
import com.tresee.backend.repository.EmpresaTieneDiaRepository;
import com.tresee.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
public class EmpresaManager {
    @Autowired
    private Gson gson;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EmpresaTieneDiaRepository empresaTieneDiaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void create(Empresa empresa) {
        empresaRepository.save(empresa);
    }

    public Empresa fromJsonCreate(String json) {
        Empresa empresa = new Empresa();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        if (jsonObject.get("idempresa") != null) {
            empresa.setIdempresa(jsonObject.get("idempresa").getAsLong());
        }
        if (jsonObject.get("nombre") != null) {
            empresa.setNombre(jsonObject.get("nombre").getAsString());
        }
        if (jsonObject.get("contacto") != null) {
            empresa.setContacto(jsonObject.get("contacto").getAsString());
        }
        if (jsonObject.get("direccion") != null) {
            empresa.setDireccion(jsonObject.get("direccion").getAsString());
        }

        if (jsonObject.get("fechaInicioPracticas") != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
            LocalDate date = LocalDate.parse(jsonObject.get("fechaInicioPracticas").getAsString(), formatter);
            empresa.setInicioPracticas(date);
        }

        return empresa;
    }

    public List<Empresa> getAll() {
        List<Empresa> toReturn = new LinkedList<>();
        for (Empresa empresa : empresaRepository.findAll()) {
            toReturn.add(empresa);
        }
        return toReturn;
    }

    public Empresa findById(Long id) {
        return this.empresaRepository.findByIdempresa(id);
    }

    public void update(Empresa empresaToModify) {
        this.empresaRepository.save(empresaToModify);
    }

    public void delete(Empresa empresa) {
        Set<Usuario> listadoUsuarios = empresa.getEstudiantes();
        for (Usuario user : listadoUsuarios) {
            user.setEmpresa(null);
            usuarioRepository.save(user);
        }
        empresa.setEstudiantes(null);

        List<EmpresaTieneDia> lista = empresa.getEmpresaTieneDias();
        for (EmpresaTieneDia intermedio : lista) {
            empresaTieneDiaRepository.delete(intermedio);
        }

        empresa.setEmpresaTieneDias(null);


        this.empresaRepository.delete(empresa);
    }
}
