package com.tresee.backend.controller;

import com.tresee.backend.enitty.Empresa;
import com.tresee.backend.manager.EmpresaManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmpresaController {

    @Autowired
    private EmpresaManager empresaManager;

    @GetMapping("/admin/empresas")
    public List<Empresa> getEmpresas() {
        return this.empresaManager.getAll();
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
}
