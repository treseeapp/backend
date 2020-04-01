package com.tresee.backend.controller;

import com.tresee.backend.enitty.Empresa;
import com.tresee.backend.enitty.Usuario;
import com.tresee.backend.manager.AmazonManager;
import com.tresee.backend.manager.EmpresaManager;
import com.tresee.backend.manager.UsuarioManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
public class EmpresaController {

    @Autowired
    private EmpresaManager empresaManager;

    @Autowired
    private AmazonManager amazonManager;

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

        Empresa empresa = empresaManager.fromJsonCreate(json);
        if (empresa.getIdempresa() == null)
            return new ResponseEntity<>("No se ha recibido ninung ID para eliminar", HttpStatus.BAD_REQUEST);
        Empresa empresaABorrar = this.empresaManager.findById(empresa.getIdempresa());
        this.empresaManager.delete(empresaABorrar);

        return new ResponseEntity<>("Empresa eliminada correctamente", HttpStatus.OK);
    }

    @PutMapping("/admin/empresas/{id}/foto")
    @Transactional
    public ResponseEntity<String> saveMyFoto(@RequestPart(value = "file") final MultipartFile uploadfile, @PathVariable Long id, HttpServletRequest request) throws IOException {

        String imageName = amazonManager.uploadFile(uploadfile);
        Empresa empresa=empresaManager.findById(id);
        System.out.println(empresa.toString());

        if (imageName != null) {
            this.amazonManager.deletePicture(empresa.getFotoEmpresa());
            empresa.setFotoEmpresa(imageName);
        }

        empresaManager.update(empresa);

        return new ResponseEntity<>("Foto subida correctamente", HttpStatus.OK);
    }

    @GetMapping("/admin/usuario/{id}foto")
    @Transactional
    public ResponseEntity<String> getMyFoto(HttpServletRequest request) {

        /*
         * Cogemos el usuario del token, asi nos aseguramos de
         * que el usuario que se modifica es a si mismo
         * */


        return new ResponseEntity<>("url", HttpStatus.OK);
    }
}
