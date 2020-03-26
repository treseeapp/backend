package com.tresee.backend.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tresee.backend.enitty.Empresa;
import com.tresee.backend.enitty.Fichaje;
import com.tresee.backend.enitty.Usuario;
import com.tresee.backend.enitty.enums.Genero;
import com.tresee.backend.repository.EmpresaRepository;
import com.tresee.backend.repository.FichajeRepository;
import com.tresee.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

@Service
public class UsuarioManager {
    @Autowired
    private Gson gson;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FichajeRepository fichajeRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    public List<Usuario> findAll() {
        List<Usuario> toReturn = new LinkedList<>();
        for (Usuario user : usuarioRepository.findAll()) {
            toReturn.add(user);
        }
        return toReturn;
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findByIdusuario(id);
    }

    public Usuario findByEmail(Long id) {
        return usuarioRepository.findByIdusuario(id);
    }

    public void update(Usuario user) {
        usuarioRepository.save(user);
    }

    public void delete(Usuario user) {

        /*
         * Tenemos que borrar todas los fichajes de este usuario
         * */
        if (user.getFichajes() != null) {
            List<Fichaje> fichajes = user.getFichajes();
            for (Fichaje individual : user.getFichajes()) {
                fichajeRepository.delete(individual);
            }
        }
        /*
         * Quitar nuestro usuario de la lista de usuarios de la empresa
         * · quitar la empresa de nuestro usuario
         * · guardar la empresa
         * */
        if (user.getEmpresa() != null) {
            Empresa empresa = user.getEmpresa();
            empresa.getEstudiantes().remove(user);
            empresa.setEstudiantes(empresa.getEstudiantes());
            user.setEmpresa(null);

            empresaRepository.save(empresa);
        }
        usuarioRepository.delete(user);
    }

    public Usuario fromJson(String json) {
        Usuario user = new Usuario();
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        if (jsonObject.get("nombre") != null) {
            user.setNombre(jsonObject.get("nombre").getAsString());
        }
        if (jsonObject.get("apellidos") != null) {
            user.setApellidos(jsonObject.get("apellidos").getAsString());
        }
        if (jsonObject.get("direccion") != null) {
            user.setDireccion(jsonObject.get("direccion").getAsString());
        }
        if (jsonObject.get("dataNacimiento") != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
            LocalDate date = LocalDate.parse(jsonObject.get("dataNacimiento").getAsString(), formatter);
            user.setDataNacimiento(date);
        }
        if (jsonObject.get("genero") != null) {
            String genero = jsonObject.get("genero").getAsString();
            if (genero.toUpperCase().equals(Genero.HOMBRE.toString())) user.setGenero(Genero.HOMBRE);
            if (genero.toUpperCase().equals(Genero.MUJER.toString())) user.setGenero(Genero.MUJER);
            if (genero.toUpperCase().equals(Genero.INDEFINIDO.toString())) user.setGenero(Genero.INDEFINIDO);
        }

        return user;
    }
}
