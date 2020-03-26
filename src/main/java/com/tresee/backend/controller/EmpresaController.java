package com.tresee.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpresaController {


    @PostMapping("/admin/empresas")
    public ResponseEntity<String> addEmpresa(@RequestBody String json) {


        return new ResponseEntity<>("Empresa creada correctamente", HttpStatus.OK);
    }
}
