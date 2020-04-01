package com.tresee.backend.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tresee.backend.enitty.Empresa;
import com.tresee.backend.enitty.Usuario;
import com.tresee.backend.manager.EmpresaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class EmpresaController {

    @Autowired
    private EmpresaManager empresaManager;

    @Autowired
    private Gson gson;

    @GetMapping("/admin/empresas")
    public List<Empresa> getEmpresas() {
        return this.empresaManager.getAll();
    }

    @GetMapping("/admin/empresas/{id}")
    public Empresa getEmpresas(@PathVariable Long id) {
        return this.empresaManager.findById(id);
    }


    @PostMapping("/admin/empresas")
    @Transactional
    public ResponseEntity<String> addEmpresa(@RequestBody String json) {
        Empresa empresa = empresaManager.fromJsonCreate(json);

        /*
         * Comprobamos que recibimos los parametros obligatorios para crear una empresa
         * */
        if (empresa.getNombre() == null
                || empresa.getContacto() == null)
            return new ResponseEntity<>("No se han recibido los parametros obligatorios", HttpStatus.BAD_REQUEST);

        empresaManager.create(empresa);
        return new ResponseEntity<>("Empresa creada correctamente", HttpStatus.OK);
    }

    @PutMapping("/admin/empresas")
    @Transactional
    public ResponseEntity<String> modifyEmpresa(@RequestBody String json) {
        Empresa empresa = empresaManager.fromJsonCreate(json);
        if (empresa.getIdempresa() == null)
            return new ResponseEntity<>("No se ha recibido ninung id para modificar", HttpStatus.BAD_REQUEST);
        if (empresa.getNombre() == null
                || empresa.getContacto() == null)
            return new ResponseEntity<>("No se han recibido los parametros obligatorios", HttpStatus.BAD_REQUEST);

        Empresa empresaToModify = this.empresaManager.findById(empresa.getIdempresa());
        empresaToModify.setNombre(empresa.getNombre());
        empresaToModify.setContacto(empresa.getContacto());
        empresaToModify.setDireccion(empresa.getDireccion());
        empresaToModify.setInicioPracticas(empresa.getInicioPracticas());

        empresaManager.update(empresaToModify);
        return new ResponseEntity<>("Empresa modificada correctamente", HttpStatus.OK);
    }


    @DeleteMapping("/admin/empresas")
    @Transactional
    public ResponseEntity<String> deleteEmpresa(@RequestBody String json) {

        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        if (jsonObject.get("idempresa") != null) {
            Empresa empresa = empresaManager.findById(jsonObject.get("idempresa").getAsLong());
            empresaManager.delete(empresa);
        }

        return new ResponseEntity<>("Empresa eliminada correctamente", HttpStatus.OK);
    }
}
